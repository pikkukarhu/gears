package fi.pa.gears.services.auth;

import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.Issuer;
import java.net.URI;

public class OidcProviderConfig {
    private final String name;
    private final Issuer issuer;
    private final ClientID clientID;
    private final String clientSecret; // Optional for some flows

    public OidcProviderConfig(String name, String issuerUri, String clientId, String clientSecret) {
        this.name = name;
        this.issuer = new Issuer(issuerUri);
        this.clientID = new ClientID(clientId);
        this.clientSecret = clientSecret;
    }

    public String getName() { return name; }
    public Issuer getIssuer() { return issuer; }
    public ClientID getClientID() { return clientID; }
    public String getClientSecret() { return clientSecret; }
}
