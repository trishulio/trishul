package io.trishul.auth.aws.client;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nonnull;

import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.amazonaws.services.cognitoidentity.model.IdentityPoolShortDescription;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import io.trishul.model.base.pojo.BaseModel;

public class CachedAwsCognitoIdentityClient implements AwsCognitoIdentityClient {
    private LoadingCache<GetIdentityPoolsArgs, List<IdentityPoolShortDescription>> getIdentityPools;
    private LoadingCache<GetIdentityIdArgs, String> getIdentityId;
    private final LoadingCache<GetCredentialsForIdentityIdArgs, Credentials> getCredentialsForIdentity;

    public CachedAwsCognitoIdentityClient(AwsCognitoIdentityClient cognitoIdClient, long credentialsExpiryDurationSeconds) {
        this.getIdentityPools = CacheBuilder.newBuilder()
                                            .build(new CacheLoader<GetIdentityPoolsArgs, List<IdentityPoolShortDescription>>() {
                                              @Override
                                              public List<IdentityPoolShortDescription> load(@Nonnull GetIdentityPoolsArgs key) throws Exception {
                                                  return cognitoIdClient.getIdentityPools(key.pageSize);
                                              }
                                            });

        this.getIdentityId = CacheBuilder.newBuilder()
                                      .build(new CacheLoader<GetIdentityIdArgs, String>() {
                                        @Override
                                        public String load(@Nonnull GetIdentityIdArgs key) throws Exception {
                                            return cognitoIdClient.getIdentityId(key.identityPoolId, key.logins);
                                        }
                                      });

        this.getCredentialsForIdentity = CacheBuilder.newBuilder()
                                      .expireAfterWrite(Duration.ofSeconds(credentialsExpiryDurationSeconds))
                                      .build(new CacheLoader<GetCredentialsForIdentityIdArgs, Credentials>() {
                                        @Override
                                        public Credentials load(@Nonnull GetCredentialsForIdentityIdArgs key) throws Exception {
                                            return cognitoIdClient.getCredentialsForIdentity(key.identityId, key.logins);
                                        }
                                      });
    }

    @Override
    public List<IdentityPoolShortDescription> getIdentityPools(int pageSize) {
        GetIdentityPoolsArgs args = new GetIdentityPoolsArgs(pageSize);

        try {
            return this.getIdentityPools.get(args);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getIdentityId(String identityPoolId, Map<String, String> logins) {
        GetIdentityIdArgs args = new GetIdentityIdArgs(identityPoolId, logins);

        try {
            return this.getIdentityId.get(args);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Credentials getCredentialsForIdentity(String identityId, Map<String, String> logins) {
        GetCredentialsForIdentityIdArgs args = new GetCredentialsForIdentityIdArgs(identityId, logins);

        try {
            return this.getCredentialsForIdentity.get(args);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

class GetIdentityPoolsArgs extends BaseModel {
    int pageSize;

    public GetIdentityPoolsArgs(int pageSize) {
        this.pageSize = pageSize;
    }
}

class GetIdentityIdArgs extends BaseModel {
    String identityPoolId;
    Map<String, String> logins;

    public GetIdentityIdArgs(String identityPoolId, Map<String, String> logins) {
        super();
        this.identityPoolId = identityPoolId;
        this.logins = logins;
    }
}

class GetCredentialsForIdentityIdArgs extends BaseModel {
    String identityId;
    Map<String, String> logins;

    public GetCredentialsForIdentityIdArgs(String identityId, Map<String, String> logins) {
        super();
        this.identityId = identityId;
        this.logins = logins;
    }
}
