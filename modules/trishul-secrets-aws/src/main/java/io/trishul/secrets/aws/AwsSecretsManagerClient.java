package io.trishul.secrets.aws;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.model.CreateSecretRequest;
import com.amazonaws.services.secretsmanager.model.DeleteSecretRequest;
import com.amazonaws.services.secretsmanager.model.DeleteSecretResult;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.model.InvalidParameterException;
import com.amazonaws.services.secretsmanager.model.InvalidRequestException;
import com.amazonaws.services.secretsmanager.model.PutSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.amazonaws.services.secretsmanager.model.UpdateSecretRequest;

import io.trishul.secrets.SecretsManager;

public class AwsSecretsManagerClient implements SecretsManager<String, String> {
    private final AWSSecretsManager client;

    private static final Logger logger = LoggerFactory.getLogger(AwsSecretsManagerClient.class);

    public AwsSecretsManagerClient(AWSSecretsManager client) {
        this.client = client;
    }

    @Override
    public String get(String secretId) throws IOException {
        GetSecretValueRequest getSecretRequest = new GetSecretValueRequest().withSecretId(secretId);
        GetSecretValueResult result;

        try {
            String secret = null;
            result = client.getSecretValue(getSecretRequest);
            if (result != null && result.getSecretString() != null) {
                secret = result.getSecretString();
            }

            return secret;

        } catch (ResourceNotFoundException e) {
            return null;
        } catch (InvalidRequestException | InvalidParameterException e) {
            logger.error("Error geting secret value for secretId: {}", secretId);
            throw new IOException(e);
        }
    }

    @Override
    public Boolean exists(String secretId) throws IOException {
        GetSecretValueRequest getSecretRequest = new GetSecretValueRequest().withSecretId(secretId);
        GetSecretValueResult getSecretValueResult = null;
        Boolean secretExists = false;

        try {
            getSecretValueResult = client.getSecretValue(getSecretRequest);
            if (getSecretValueResult != null && getSecretValueResult.getSecretString() != null) {
                secretExists = true;
            }
        } catch (ResourceNotFoundException e) {
            secretExists = false;
        } catch (InvalidRequestException | InvalidParameterException e) {
            logger.error("Error checking if secret exists for secretId: {}", secretId);
            throw new IOException(e);
        }

        return secretExists;
    }

    @Override
    public void put(String secretId, String secret) throws IOException {
        Boolean secretExists = this.exists(secretId);

        if (!secretExists) {
            this.create(secretId, secret);
        } else {
            PutSecretValueRequest updateSecretRequest = new PutSecretValueRequest().withSecretId(secretId).withSecretString(secret);

            try {
                client.putSecretValue(updateSecretRequest);
            } catch (InvalidRequestException | InvalidParameterException e) {
                logger.error("Error updating secret with secretId: {}", secretId);
                throw new IOException(e);
            }
        }
    }

    @Override
    public void create(String secretId, String secret) throws IOException {
        CreateSecretRequest createSecretRequest = new CreateSecretRequest().withName(secretId).withSecretString(secret);

        try {
            client.createSecret(createSecretRequest);
        } catch (InvalidRequestException | InvalidParameterException e) {
            logger.error("Error createing secret with secretId: {}", secretId);
            throw new IOException(e);
        }
    }

    @Override
    public void update(String secretId, String secret) throws IOException {
        UpdateSecretRequest updateSecretRequest = new UpdateSecretRequest().withSecretId(secretId).withSecretString(secret);

        try {
            client.updateSecret(updateSecretRequest);
        } catch (InvalidRequestException | InvalidParameterException e) {
            logger.error("Error updating secret with secretId: {}", secretId);
            throw new IOException(e);
        }
    }

    @Override
    public boolean remove(String secretId) throws IOException {
        boolean success = false;
        try {
            DeleteSecretRequest req = new DeleteSecretRequest().withSecretId(secretId);
            DeleteSecretResult result = client.deleteSecret(req);
            success = true;
        } catch (ResourceNotFoundException e) {
            logger.error("Error deleting secret: {}", secretId);
        }

        return success;
    }
}
