# trishul-iaas-tenant-object-store-aws

## Overview
- IaaS Tenant Object Store
- Designed to be consumed as a standalone dependency (or composed with other Trishul modules).

## Features & Capabilities
- Library module (consume types directly; may still include auto-config classes).
- Configurable via `*-application.properties` (importable into your app).

## Artifact Coordinates

```xml
<dependency>
  <groupId>sh.trishul</groupId>
  <artifactId>trishul-iaas-tenant-object-store-aws</artifactId>
</dependency>
```

## Dependencies

### Internal Dependencies
- [trishul-iaas-access](../trishul-iaas-access/README.md)
- [trishul-iaas-tenant](../trishul-iaas-tenant/README.md)
- [trishul-iaas-tenant-idp](../trishul-iaas-tenant-idp/README.md)
- [trishul-object-store](../trishul-object-store/README.md)
- [trishul-test-bom](../trishul-test-bom/README.md)

### External Dependencies
- None

## Configuration

### Application Properties

```properties
aws.s3.config.cors.allowed.headers=*
aws.s3.config.cors.allowed.methods=GET;PUT;POST;DELETE
aws.s3.config.cors.allowed.origins=${APP_HOST_URLS}
aws.s3.config.access.public.acls.block=true
aws.s3.config.access.public.acls.ignore=true
aws.s3.config.access.public.policy.block=true
aws.s3.config.access.public.buckets.restrict=true
```

### Environment Variables

```bash
export APP_HOST_URLS=...
```

## AutoConfiguration Beans

- No Spring `@Bean` methods detected in this module.

## Key Classes & APIs

- (No `src/main/java` classes found)

## Tests (Examples)

- (No `*Test.java` classes found)

## Integration Guide

### 1) Add Dependency
Use the Maven dependency shown above.

### 2) Import Module Properties

```properties
spring.config.import=classpath:iaas-tenant-object-store-aws-application.properties
```

Note: the actual file name in this module is: `iaas-tenant-object-store-aws-application.properties`.

### 3) Use Beans / Types
- If this module provides Spring beans, they will be auto-registered by Spring Boot auto-configuration.
- If it’s a pure library module (no beans), consume the types directly.

## Consumers (Modules That Depend On This)

- None

## Related Modules

- See the full module map in [TRISHUL_MODULE_ANALYSIS.md](../../TRISHUL_MODULE_ANALYSIS.md)
