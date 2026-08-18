package sh.trishul.object.store.file.service.aws.client.provider;

import com.amazonaws.services.s3.AmazonS3;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.annotation.Nonnull;
import java.net.URI;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import sh.trishul.iaas.auth.session.context.ContextHolderAuthorizationFetcher;
import sh.trishul.iaas.auth.session.context.IaasAuthorization;
import sh.trishul.iaas.client.IaasClient;
import sh.trishul.iaas.client.SequentialExecutor;
import sh.trishul.iaas.repository.IaasRepository;
import sh.trishul.iaas.repository.provider.IaasRepositoryProvider;
import sh.trishul.model.base.pojo.BaseModel;
import sh.trishul.model.mapper.LocalDateTimeMapper;
import sh.trishul.object.store.file.model.BaseIaasObjectStoreFile;
import sh.trishul.object.store.file.model.IaasObjectStoreFile;
import sh.trishul.object.store.file.model.UpdateIaasObjectStoreFile;
import sh.trishul.object.store.file.service.aws.client.AwsS3FileClient;
import sh.trishul.object.store.file.service.aws.factory.ObjectStoreFileServiceAwsFactory;
import sh.trishul.object.store.file.service.service.IaasObjectStoreNameProvider;

public class TenantContextAwsObjectStoreFileClientProvider implements
    IaasRepositoryProvider<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>> {
  // Because the IaasAuthorization expires in 1 hour so the existing client
  // becomes useless.
  public static final Duration DURATION_REMOVE_UNUSED_CLIENTS = Duration.ofHours(1);

  private final String region;
  private final ContextHolderAuthorizationFetcher authFetcher;
  private final LoadingCache<GetAmazonS3ClientArgs, IaasRepository<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>>> cache;
  private final IaasObjectStoreNameProvider bucketNameProvider;

  public TenantContextAwsObjectStoreFileClientProvider(String region,
      IaasObjectStoreNameProvider bucketNameProvider, ContextHolderAuthorizationFetcher authFetcher,
      ObjectStoreFileServiceAwsFactory awsFactory, Long getPresignUrlDuration) {
    this.region = region;
    this.authFetcher = authFetcher;
    this.bucketNameProvider = bucketNameProvider;

    // TODO: Instead of returning whole repository, implement a ClientProxy that
    // refreshes
    // clients
    this.cache = CacheBuilder.newBuilder().expireAfterWrite(DURATION_REMOVE_UNUSED_CLIENTS).build(
        new CacheLoader<GetAmazonS3ClientArgs, IaasRepository<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>>>() {
          @Override
          public IaasRepository<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>> load(
              @Nonnull GetAmazonS3ClientArgs args) throws Exception {
            AmazonS3 s3Client = awsFactory.s3Client(args.region, args.accessKeyId,
                args.accessSecretKey, args.sessionToken);
            IaasClient<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>> client
                = new AwsS3FileClient(s3Client, args.bucketName, LocalDateTimeMapper.INSTANCE,
                    getPresignUrlDuration);

            return new SequentialExecutor<>(client);
          }
        });
  }

  @Override
  public IaasRepository<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile<?>, UpdateIaasObjectStoreFile<?>> getIaasRepository() {
    IaasAuthorization authorization = authFetcher.fetch();

    GetAmazonS3ClientArgs args = new GetAmazonS3ClientArgs(this.region,
        bucketNameProvider.getObjectStoreName(), authorization.getAccessKeyId(),
        authorization.getAccessSecretKey(), authorization.getSessionToken());

    try {
      return this.cache.get(args);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}


class GetAmazonS3ClientArgs extends BaseModel {
  String region;
  String bucketName;
  String accessKeyId;
  String accessSecretKey;
  String sessionToken;

  public GetAmazonS3ClientArgs(String region, String bucketName, String accessKeyId,
      String accessSecretKey, String sessionToken) {
    this.region = region;
    this.bucketName = bucketName;
    this.accessKeyId = accessKeyId;
    this.accessSecretKey = accessSecretKey;
    this.sessionToken = sessionToken;
  }
}
