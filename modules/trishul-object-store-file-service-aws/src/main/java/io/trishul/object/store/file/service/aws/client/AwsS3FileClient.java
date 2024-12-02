package io.trishul.object.store.file.service.aws.client;

import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import io.trishul.model.base.pojo.BaseModel;
import io.trishul.model.mapper.LocalDateTimeMapper;
import io.trishul.iaas.client.IaasClient;
import io.trishul.object.store.file.service.model.entity.BaseIaasObjectStoreFile;
import io.trishul.object.store.file.service.model.entity.IaasObjectStoreFile;
import io.trishul.object.store.file.service.model.entity.UpdateIaasObjectStoreFile;

public class AwsS3FileClient implements IaasClient<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile> {
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(AwsS3FileClient.class);

    private final LoadingCache<PresignUrlRequest, IaasObjectStoreFile> presignUrlCache;
    private final AmazonS3 s3;
    private final String bucketName;

    private final long getDuration;

    public AwsS3FileClient(AmazonS3 s3, String bucketName, LocalDateTimeMapper dtMapper, long getDuration) {
        this.s3 = s3;
        this.bucketName = bucketName;
        this.presignUrlCache = CacheBuilder.newBuilder()
                                            .expireAfterWrite(Duration.ofHours(1))
                                            .build(new CacheLoader<PresignUrlRequest, IaasObjectStoreFile>(){
            @Override
            public IaasObjectStoreFile load(@Nonnull PresignUrlRequest key) throws Exception {
                IaasObjectStoreFile file = new IaasObjectStoreFile(URI.create(key.fileKey), key.expiration, null);

                GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, file.getFileKey().toString())
                        .withMethod(key.method)
                        .withExpiration(dtMapper.toUtilDate(file.getExpiration()));

                URL url = s3.generatePresignedUrl(req);

                return new IaasObjectStoreFile(URI.create(key.fileKey), key.expiration, url);
            }
        });

        this.getDuration = getDuration;
    }

    @Override
    public IaasObjectStoreFile get(URI id) {
        // Note: Due to the signature of the interface implemented, GET requests only
        // takes ID as an input. So we pass in a default expiration for this case.
        LocalDateTime expiration = LocalDateTime.now().plusSeconds(this.getDuration);
        return presign(id.toString(), expiration, HttpMethod.GET);
    }

    @Override
    public <BE extends BaseIaasObjectStoreFile> IaasObjectStoreFile add(BE addition) {
        String fileKey = UUID.randomUUID().toString();
        return presign(fileKey, addition.getExpiration(), HttpMethod.PUT);
    }

    @Override
    public <UE extends UpdateIaasObjectStoreFile> IaasObjectStoreFile put(UE update) {
        return presign(update.getFileKey().toString(), update.getExpiration(), HttpMethod.PUT);
    }

    @Override
    public boolean delete(URI id) {
        boolean exists = exists(id);
        if (exists) {
            s3.deleteObject(new DeleteObjectRequest(this.bucketName, id.toString()));
        }

        return exists;
    }

    @Override
    public boolean exists(URI id) {
        return s3.doesObjectExist(bucketName, id.toString());
    }

    private IaasObjectStoreFile presign(String fileKey, LocalDateTime expiration, HttpMethod method) {
        PresignUrlRequest request = new PresignUrlRequest(fileKey, expiration, method);
        try {
            return this.presignUrlCache.get(request);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

class PresignUrlRequest extends BaseModel {
    final String fileKey;
    final LocalDateTime expiration;
    final HttpMethod method;

    public PresignUrlRequest(String fileKey, LocalDateTime expiration, HttpMethod method) {
        this.fileKey = fileKey;
        this.expiration = expiration;
        this.method = method;
    }
}
