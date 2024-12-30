package io.trishul.object.store.service.aws.cors.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;
import com.amazonaws.services.s3.model.DeleteBucketCrossOriginConfigurationRequest;
import com.amazonaws.services.s3.model.GetBucketCrossOriginConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketCrossOriginConfigurationRequest;
import io.trishul.iaas.client.IaasClient;
import io.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AwsCorsConfigClient implements
    IaasClient<String, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration, IaasObjectStoreCorsConfiguration> {
  private static final Logger log = LoggerFactory.getLogger(AwsCorsConfigClient.class);

  private final AmazonS3 awsClient;

  public AwsCorsConfigClient(final AmazonS3 awsClient) {
    this.awsClient = awsClient;
  }

  @Override
  public IaasObjectStoreCorsConfiguration get(String bucketName) {
    final GetBucketCrossOriginConfigurationRequest request
        = new GetBucketCrossOriginConfigurationRequest(bucketName);

    BucketCrossOriginConfiguration bucketCrossOriginConfiguration = null;
    try {
      bucketCrossOriginConfiguration = awsClient.getBucketCrossOriginConfiguration(request);
    } catch (AmazonS3Exception e) {
      log.error("Failed to get the cross origin configuration for bucket: {}", bucketName);
    }

    IaasObjectStoreCorsConfiguration iaasObjectStoreCorsConfiguration
        = bucketCrossOriginConfiguration == null ? null
            : new IaasObjectStoreCorsConfiguration(bucketName, bucketCrossOriginConfiguration);
    return iaasObjectStoreCorsConfiguration;
  }

  @Override
  public <BE extends IaasObjectStoreCorsConfiguration> IaasObjectStoreCorsConfiguration add(
      BE entity) {
    log.debug("Replacing existing CORS setting on the bucket: {} (if any) since "
        + "only single CORS configuration can be applied.", entity.getBucketName());
    return this.put(entity);
  }

  @Override
  public <UE extends IaasObjectStoreCorsConfiguration> IaasObjectStoreCorsConfiguration put(
      UE entity) {
    final SetBucketCrossOriginConfigurationRequest request
        = new SetBucketCrossOriginConfigurationRequest(entity.getBucketName(),
            entity.getBucketCrossOriginConfiguration());
    try {
      this.awsClient.setBucketCrossOriginConfiguration(request);
    } catch (AmazonS3Exception e) {
      if (awsClient.doesBucketExistV2(entity.getBucketName())
          && e.getErrorCode().equalsIgnoreCase("AccessDenied")) {
        String msg = String.format("Bucket: '%s' exists, but failed to put the cross origin "
            + "configuration. S3 buckets name are universal (i.e. not specific "
            + "to this AWS account). This is likely because the bucket already "
            + "exists in a different account. You might already have a Tenant with "
            + "this ID on a different AWS account. Recommendation is to use a "
            + "different Tenant ID for this environment.", entity.getBucketName());
        log.error(msg);
        e.setErrorMessage(e.getErrorMessage() + "; " + msg);
      }

      log.error("Failed to put the cross origin configuration for bucket: {}",
          entity.getBucketName());
      throw e;
    }

    return this.get(entity.getBucketName());
  }

  @Override
  public boolean exists(String bucketName) {
    return get(bucketName) != null;
  }

  @Override
  public boolean delete(final String bucketName) {
    boolean success = false;

    final DeleteBucketCrossOriginConfigurationRequest request
        = new DeleteBucketCrossOriginConfigurationRequest(bucketName);
    try {
      this.awsClient.deleteBucketCrossOriginConfiguration(request);
      success = true;
    } catch (AmazonS3Exception e) {
      log.error("Failed to delete the cross origin configuration for bucket: {}", bucketName);
    }

    return success;
  }
}
