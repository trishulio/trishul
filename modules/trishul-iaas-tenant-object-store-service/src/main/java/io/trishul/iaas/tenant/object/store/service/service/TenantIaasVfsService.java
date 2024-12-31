package io.trishul.iaas.tenant.object.store.service.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.trishul.iaas.access.policy.model.BaseIaasPolicy;
import io.trishul.iaas.access.policy.model.IaasPolicy;
import io.trishul.iaas.access.policy.model.UpdateIaasPolicy;
import io.trishul.iaas.access.role.attachment.policy.BaseIaasRolePolicyAttachment;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachment;
import io.trishul.iaas.access.role.attachment.policy.IaasRolePolicyAttachmentId;
import io.trishul.iaas.access.role.attachment.policy.UpdateIaasRolePolicyAttachment;
import io.trishul.iaas.access.service.policy.service.IaasPolicyService;
import io.trishul.iaas.access.service.role.policy.attachment.service.IaasRolePolicyAttachmentService;
import io.trishul.iaas.idp.tenant.model.BaseIaasIdpTenant;
import io.trishul.iaas.idp.tenant.model.UpdateIaasIdpTenant;
import io.trishul.iaas.tenant.object.store.TenantIaasVfsDeleteResult;
import io.trishul.iaas.tenant.object.store.TenantIaasVfsResourceMapper;
import io.trishul.iaas.tenant.object.store.TenantIaasVfsResources;
import io.trishul.iaas.tenant.object.store.builder.TenantObjectStoreResourceBuilder;
import io.trishul.object.store.configuration.access.model.IaasObjectStoreAccessConfig;
import io.trishul.object.store.configuration.cors.model.IaasObjectStoreCorsConfiguration;
import io.trishul.object.store.model.BaseIaasObjectStore;
import io.trishul.object.store.model.IaasObjectStore;
import io.trishul.object.store.model.UpdateIaasObjectStore;
import io.trishul.object.store.service.IaasObjectStoreService;
import io.trishul.object.store.service.cors.config.service.IaasObjectStoreAccessConfigService;
import io.trishul.object.store.service.cors.config.service.IaasObjectStoreCorsConfigService;

public class TenantIaasVfsService {
  private static final Logger log = LoggerFactory.getLogger(TenantIaasVfsService.class);

  private final TenantIaasVfsResourceMapper mapper;
  private final IaasPolicyService policyService;
  private final IaasRolePolicyAttachmentService rolePolicyAttachmentService;
  private final IaasObjectStoreService objectStoreService;
  private final IaasObjectStoreCorsConfigService objectStoreCorsConfigService;
  private final IaasObjectStoreAccessConfigService objectStoreAccessConfigService;

  private final TenantObjectStoreResourceBuilder resourceBuilder;

  public TenantIaasVfsService(TenantIaasVfsResourceMapper mapper, IaasPolicyService policyService,
      IaasObjectStoreService objectStoreService,
      IaasRolePolicyAttachmentService rolePolicyAttachmentService,
      IaasObjectStoreCorsConfigService objectStoreCorsConfigService,
      IaasObjectStoreAccessConfigService objectStoreAccessConfigService,
      TenantObjectStoreResourceBuilder resourceBuilder) {
    this.mapper = mapper;
    this.policyService = policyService;
    this.objectStoreService = objectStoreService;
    this.rolePolicyAttachmentService = rolePolicyAttachmentService;
    this.objectStoreCorsConfigService = objectStoreCorsConfigService;
    this.objectStoreAccessConfigService = objectStoreAccessConfigService;
    this.resourceBuilder = resourceBuilder;
  }

  public List<TenantIaasVfsResources> get(Set<String> iaasIdpTenantIds) {
    Set<String> policyIds = new HashSet<>();
    Set<String> objectStoreIds = new HashSet<>();

    iaasIdpTenantIds.stream().forEach(iaasIdpTenantId -> {
      String policyId = this.resourceBuilder.getVfsPolicyId(iaasIdpTenantId);
      policyIds.add(policyId);

      String objectStoreId = this.resourceBuilder.getObjectStoreId(iaasIdpTenantId);
      objectStoreIds.add(objectStoreId);
    });

    List<IaasPolicy> policies = this.policyService.getAll(policyIds);
    List<IaasObjectStore> objectStores = this.objectStoreService.getAll(objectStoreIds);

    return mapper.fromComponents(objectStores, policies);
  }

  public List<TenantIaasVfsResources> add(List<? extends BaseIaasIdpTenant<?>> tenants) {
    List<BaseIaasObjectStore<?>> objectStoreAdditions = new ArrayList<>(tenants.size());
    List<BaseIaasPolicy<?>> policiesAdditions = new ArrayList<>(tenants.size());

    tenants.forEach(tenant -> {
      BaseIaasObjectStore<?> objectStore = this.resourceBuilder.buildObjectStore(tenant);
      objectStoreAdditions.add(objectStore);

      BaseIaasPolicy<?> policy = this.resourceBuilder.buildVfsPolicy(tenant);
      policiesAdditions.add(policy);
    });

    List<IaasPolicy> policies = this.policyService.add(policiesAdditions);
    List<IaasObjectStore> objectStores = this.objectStoreService.add(objectStoreAdditions);

    Iterator<IaasPolicy> policiesIterator = policies.iterator();

    List<? extends BaseIaasRolePolicyAttachment<?>> attachmentAdditions
        = tenants.stream().map(tenant -> this.resourceBuilder.buildAttachment(tenant.getIaasRole(),
            policiesIterator.next())).toList();

    List<IaasRolePolicyAttachment> attachments
        = this.rolePolicyAttachmentService.add(attachmentAdditions);
    log.info(String.format("Created attachments: %s", attachments.size()));

    List<IaasObjectStoreCorsConfiguration> objectStoreCorsConfigUpdates
        = tenants.stream().map(this.resourceBuilder::buildObjectStoreCorsConfiguration).toList();

    List<IaasObjectStoreCorsConfiguration> objectStoreCorsConfigs
        = this.objectStoreCorsConfigService.add(objectStoreCorsConfigUpdates);
    log.info(
        String.format("Created ObjectStoreCorsConfigurations: %s", objectStoreCorsConfigs.size()));

    List<IaasObjectStoreAccessConfig> objectStoreAccessConfigUpdates
        = tenants.stream().map(this.resourceBuilder::buildPublicAccessBlock).toList();

    List<IaasObjectStoreAccessConfig> objectStoreAccessConfigs
        = this.objectStoreAccessConfigService.add(objectStoreAccessConfigUpdates);

    log.info(
        String.format("Created IaasObjectStoreAccessConfig: %s", objectStoreAccessConfigs.size()));
    return this.mapper.fromComponents(objectStores, policies);
  }

  public List<TenantIaasVfsResources> put(List<? extends UpdateIaasIdpTenant<?>> tenants) {
    List<UpdateIaasObjectStore<?>> objectStoreUpdates = new ArrayList<>(tenants.size());
    List<UpdateIaasPolicy<?>> policiesUpdates = new ArrayList<>(tenants.size());

    tenants.forEach(tenant -> {
      UpdateIaasObjectStore<?> objectStore
          = (UpdateIaasObjectStore<?>) this.resourceBuilder.buildObjectStore(tenant);
      objectStoreUpdates.add(objectStore);

      UpdateIaasPolicy<?> policy
          = (UpdateIaasPolicy<?>) this.resourceBuilder.buildVfsPolicy(tenant);
      policiesUpdates.add(policy);
    });

    List<IaasPolicy> policies = this.policyService.put(policiesUpdates);
    List<IaasObjectStore> objectStores = this.objectStoreService.put(objectStoreUpdates);

    Iterator<IaasPolicy> policiesIterator = policies.iterator();

    List<? extends UpdateIaasRolePolicyAttachment<?>> attachmentUpdates
        = tenants.stream().map(tenant -> this.resourceBuilder.buildAttachment(tenant.getIaasRole(),
            policiesIterator.next())).map(o -> (UpdateIaasRolePolicyAttachment<?>) o).toList();

    List<IaasRolePolicyAttachment> attachments
        = this.rolePolicyAttachmentService.put(attachmentUpdates);
    log.info(String.format("Created IaasRolePolicyAttachment: %s", attachments.size()));

    List<IaasObjectStoreCorsConfiguration> objectStoreCorsConfigUpdates = tenants.stream()
        .map(tenant -> this.resourceBuilder.buildObjectStoreCorsConfiguration(tenant))
        .map(o -> (IaasObjectStoreCorsConfiguration) o).toList();

    List<IaasObjectStoreCorsConfiguration> objectStoreCorsConfigs
        = this.objectStoreCorsConfigService.put(objectStoreCorsConfigUpdates);
    log.info(String.format("Created IaasObjectStoreCorsConfiguration: %s",
        objectStoreCorsConfigs.size()));

    List<IaasObjectStoreAccessConfig> objectStoreAccessConfigUpdates
        = tenants.stream().map(this.resourceBuilder::buildPublicAccessBlock).toList();

    List<IaasObjectStoreAccessConfig> objectStoreAccessConfigs
        = this.objectStoreAccessConfigService.put(objectStoreAccessConfigUpdates);
    log.info(
        String.format("Created IaasObjectStoreAccessConfig: %s", objectStoreAccessConfigs.size()));

    return this.mapper.fromComponents(objectStores, policies);
  }

  public TenantIaasVfsDeleteResult delete(Set<String> iaasIdpTenantIds) {
    Set<String> objectStoreIds = new HashSet<>();
    Set<String> policyIds = new HashSet<>();
    Set<IaasRolePolicyAttachmentId> attachmentIds = new HashSet<>();

    iaasIdpTenantIds.stream().forEach(iaasIdpTenantId -> {
      String policyId = this.resourceBuilder.getVfsPolicyId(iaasIdpTenantId);
      policyIds.add(policyId);

      IaasRolePolicyAttachmentId attachmentId
          = this.resourceBuilder.buildVfsAttachmentId(iaasIdpTenantId);
      attachmentIds.add(attachmentId);

      String objectStoreId = this.resourceBuilder.getObjectStoreId(iaasIdpTenantId);
      objectStoreIds.add(objectStoreId);
    });

    this.rolePolicyAttachmentService.delete(attachmentIds);
    this.objectStoreCorsConfigService.delete(objectStoreIds);
    this.objectStoreAccessConfigService.delete(objectStoreIds);
    long policyCount = this.policyService.delete(policyIds);
    long objectStoreCount = this.objectStoreService.delete(objectStoreIds);

    return new TenantIaasVfsDeleteResult(policyCount, objectStoreCount);
  }
}
