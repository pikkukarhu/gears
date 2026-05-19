package fi.pa.gears.services.auth;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jwt.JWT;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.AuthorizationCodeGrant;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.TokenErrorResponse;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.openid.connect.sdk.Nonce;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponse;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponseParser;
import com.nimbusds.openid.connect.sdk.claims.IDTokenClaimsSet;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.token.OIDCTokens;
import com.nimbusds.openid.connect.sdk.validators.IDTokenValidator;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class OidcService {

    private final Map<String, OidcProviderConfig> providers = new HashMap<>();
    private final Map<String, OIDCProviderMetadata> providerMetadataCache = new HashMap<>();

    public void addProvider(OidcProviderConfig config) {
        providers.put(config.getName(), config);
    }

    public OidcProviderConfig getProviderConfig(String providerName) {
        return providers.get(providerName);
    }

    public OIDCProviderMetadata getMetadata(String providerName) throws IOException, ParseException {
        if (providerMetadataCache.containsKey(providerName)) {
            return providerMetadataCache.get(providerName);
        }

        OidcProviderConfig config = providers.get(providerName);
        if (config == null) {
            throw new IllegalArgumentException("Unknown provider: " + providerName);
        }

        URL discoveryUrl = new URL(config.getIssuer().getValue() + "/.well-known/openid-configuration");
        try (InputStream is = discoveryUrl.openStream()) {
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            String json = s.hasNext() ? s.next() : "";
            OIDCProviderMetadata metadata = OIDCProviderMetadata.parse(json);
            providerMetadataCache.put(providerName, metadata);
            return metadata;
        }
    }

    public OIDCTokens tokenExchange(String providerName, AuthorizationCode code, URI redirectUri) 
            throws Exception {
        
        OidcProviderConfig config = providers.get(providerName);
        OIDCProviderMetadata metadata = getMetadata(providerName);

        TokenRequest request = new TokenRequest(
                metadata.getTokenEndpointURI(),
                new ClientSecretBasic(config.getClientID(), new Secret(config.getClientSecret())),
                new AuthorizationCodeGrant(code, redirectUri)
        );

        TokenResponse response = OIDCTokenResponseParser.parse(request.toHTTPRequest().send());

        if (!response.indicatesSuccess()) {
            TokenErrorResponse errorResponse = response.toErrorResponse();
            throw new Exception("Token exchange failed: " + errorResponse.getErrorObject().getDescription());
        }

        return ((OIDCTokenResponse) response).getOIDCTokens();
    }

    public IDTokenClaimsSet validateIdToken(String providerName, JWT idToken, Nonce expectedNonce) 
            throws Exception {
        
        OidcProviderConfig config = providers.get(providerName);
        OIDCProviderMetadata metadata = getMetadata(providerName);

        // Get the JWK set URL from metadata
        URL jwkSetURL = metadata.getJWKSetURI().toURL();

        // Create validator
        // We assume RS256 as it's the most common for OIDC
        IDTokenValidator validator = new IDTokenValidator(
                config.getIssuer(), 
                config.getClientID(), 
                JWSAlgorithm.RS256, 
                jwkSetURL);

        return validator.validate(idToken, expectedNonce);
    }
}
