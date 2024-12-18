package io.trishul.object.store.service.aws.cors.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.DeleteBucketRequest;
import com.amazonaws.services.s3.model.ListBucketsRequest;
import io.trishul.iaas.client.IaasClient;
import io.trishul.iaas.mapper.IaasEntityMapper;
import io.trishul.object.store.model.BaseIaasObjectStore;
import io.trishul.object.store.model.IaasObjectStore;
import io.trishul.object.store.model.UpdateIaasObjectStore;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AwsObjectStoreClient
        implements IaasClient<String, IaasObjectStore, BaseIaasObjectStore, UpdateIaasObjectStore> {
    private static final Logger log = LoggerFactory.getLogger(AwsObjectStoreClient.class);

    private final AmazonS3 awsClient;
    private final InheritableThreadLocal<Map<String, IaasObjectStore>> localCache;
    private final IaasEntityMapper<Bucket, IaasObjectStore> mapper;

    public AwsObjectStoreClient(
            AmazonS3 awsClient, IaasEntityMapper<Bucket, IaasObjectStore> mapper) {
        this.localCache = new InheritableThreadLocal<>();
        this.awsClient = awsClient;
        this.mapper = mapper;
    }

    @Override
    public IaasObjectStore get(String bucketName) {
        return objectStores().get(bucketName);
    }

    @Override
    public boolean delete(String bucketName) {
        boolean success = false;
        try {
            DeleteBucketRequest request = new DeleteBucketRequest(bucketName);
            this.awsClient.deleteBucket(request);
            success = true;
        } catch (AmazonS3Exception e) {
            log.error("Failed to delete the objectStore: {}", bucketName);
        } finally {
            reset();
        }

        return success;
    }

    @Override
    public IaasObjectStore add(BaseIaasObjectStore objectStore) {
        CreateBucketRequest request = new CreateBucketRequest(objectStore.getName());

        Bucket bucket = this.awsClient.createBucket(request);

        reset();
        return this.mapper.fromIaasEntity(bucket);
    }

    @Override
    public IaasObjectStore put(UpdateIaasObjectStore objectStore) {
        if (exists(objectStore.getName())) {
            return get(objectStore.getName());
        } else {
            return add(objectStore);
        }
    }

    @Override
    public boolean exists(String bucketName) {
        return awsClient.doesBucketExistV2(bucketName);
    }

    private Map<String, IaasObjectStore> objectStores() {
        Map<String, IaasObjectStore> cache = localCache.get();

        if (cache == null) {
            List<Bucket> buckets = awsClient.listBuckets(new ListBucketsRequest());
            if (buckets != null) {
                cache =
                        buckets.stream()
                                .map(mapper::fromIaasEntity)
                                .collect(
                                        Collectors.toMap(
                                                IaasObjectStore::getName, Function.identity()));
            }
            localCache.set(cache);
        }

        return cache;
    }

    private void reset() {
        localCache.set(null);
    }
}
