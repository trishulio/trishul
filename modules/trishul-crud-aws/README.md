# trishul-crud-aws

AWS exception handling for CRUD REST controllers.

> **Use this when**: Your API uses AWS services and you need consistent error responses for AWS exceptions.

## Installation

```xml
<dependency>
    <groupId>io.trishul</groupId>
    <artifactId>trishul-crud-aws</artifactId>
</dependency>
```

## Quick Start

```java
// 1. Add the dependency - exception handler is auto-registered via @RestControllerAdvice
// 2. AWS exceptions are automatically converted to HTTP 500 responses

// When your code throws AmazonServiceException:
s3Client.getObject("nonexistent-bucket", "key");  // Throws AmazonServiceException

// The handler returns a structured error response:
// {
//   "timestamp": "2024-01-15T10:30:00",
//   "status": 500,
//   "error": "Internal Server Error",
//   "message": "Failed to call AWS, received ErrorCode: NoSuchBucket; StatusCode: 404; Message: ...",
//   "path": "/api/v1/files/123"
// }
```

## When Would I Use This?

### When you need consistent error responses for AWS failures

The `ControllerAwsExceptionHandler` catches all `AmazonServiceException` errors:

```java
@RestController
@RequestMapping("/api/v1/files")
public class FileController {
    
    @Autowired
    private AmazonS3 s3Client;
    
    @GetMapping("/{key}")
    public FileDto getFile(@PathVariable String key) {
        // If S3 throws an exception, it's automatically caught
        // and converted to a proper HTTP error response
        S3Object object = s3Client.getObject(bucketName, key);
        return mapToDto(object);
    }
}
```

### When you want to log AWS errors with context

The handler logs detailed error information:

```
ERROR - Failed to call AWS, received ErrorCode: AccessDenied; StatusCode: 403; Message: Access Denied
```

### When you need to expose AWS error codes to clients

The error response includes AWS-specific details:

```java
// Response body structure
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Failed to call AWS, received ErrorCode: ThrottlingException; StatusCode: 429; Message: Rate exceeded",
  "path": "/api/v1/buckets"
}
```

## Error Response Format

| Field | Type | Description |
|-------|------|-------------|
| `timestamp` | `LocalDateTime` | When the error occurred |
| `status` | `int` | HTTP status code (500) |
| `error` | `String` | HTTP status reason phrase |
| `message` | `String` | Detailed error with AWS error code and status |
| `path` | `String` | Request URI that caused the error |

## Core Components

| Class | Purpose |
|-------|---------|
| `ControllerAwsExceptionHandler` | `@RestControllerAdvice` for `AmazonServiceException` |
| `ErrorResponse` | Structured error response DTO (from `trishul-crud`) |

## Handled Exceptions

| Exception | HTTP Status | Description |
|-----------|-------------|-------------|
| `AmazonServiceException` | 500 | All AWS service errors |

## Complete Example

```java
@RestController
@RequestMapping("/api/v1/storage")
public class StorageController {
    
    private final AmazonS3 s3Client;
    
    @PostMapping("/buckets")
    public BucketDto createBucket(@RequestBody CreateBucketRequest request) {
        // If bucket creation fails due to AWS issues,
        // ControllerAwsExceptionHandler returns proper error response
        Bucket bucket = s3Client.createBucket(request.getName());
        return mapToDto(bucket);
    }
    
    @DeleteMapping("/buckets/{name}")
    public void deleteBucket(@PathVariable String name) {
        // Access denied, bucket not found, etc. are all handled
        s3Client.deleteBucket(name);
    }
}

// No try-catch needed - the @RestControllerAdvice handles it globally
```

## Related Modules

| Module | Relationship |
|--------|--------------|
| `trishul-crud` | Base CRUD controller infrastructure |
| `trishul-object-store-aws` | S3 object storage |
| `trishul-secrets-aws` | AWS Secrets Manager |
