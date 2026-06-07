# trishul-ai-service

Multi-tenant AI service module providing chat, speech, and agent capabilities using LangChain4j.

## Configuration

### Encryption

The module supports encryption for sensitive data stored in the database (e.g., AI model API keys).

| Property | Description | Default |
|----------|-------------|---------|
| `db.encryption.algorithm` | The AES algorithm and padding to use. | `AES/ECB/PKCS5Padding` |
| `db.encryption.key` | The secret key used for encryption/decryption. Should be 16 bytes for AES-128. | `${TRISHUL_DB_ENCRYPTION_KEY}` |

#### Production Setup

In production, ensure the `TRISHUL_DB_ENCRYPTION_KEY` environment variable is set to a secure, 16-character string.

#### Development/Test Setup

For tests, a dummy key `test-key-12345678` is used via `ai-service-application-test.properties`.
