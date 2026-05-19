package fi.pa.gears.services;

import fi.pa.gears.services.auth.OidcProviderConfig;
import fi.pa.gears.services.auth.OidcService;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.openid.connect.sdk.Nonce;
import com.nimbusds.openid.connect.sdk.claims.IDTokenClaimsSet;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.nimbusds.openid.connect.sdk.token.OIDCTokens;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/oauth")
public class LoginService {

    public static final String APPLICATION_NAME = "gears-210218";
    
    private static final OidcService oidcService = new OidcService();
    static {
        // Example: Adding Google as a provider with its Client Secret for Code Flow.
        oidcService.addProvider(new OidcProviderConfig(
            "google", 
            "https://accounts.google.com", 
            "80119940762-t8haqe16i92kml0cqv30e81vuq07a4cq.apps.googleusercontent.com", 
            "_B2qVKFO__T07oNB7ZQ5wJsS" 
        ));
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("login/{provider}")
    public Response login(@PathParam("provider") String providerName, @Context HttpServletRequest request) throws Exception {
        
        OIDCProviderMetadata metadata = oidcService.getMetadata(providerName);
        OidcProviderConfig config = oidcService.getProviderConfig(providerName);
        
        if (config == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Unknown provider: " + providerName).build();
        }

        URI authEndpoint = metadata.getAuthorizationEndpointURI();
        
        State state = new State();
        Nonce nonce = new Nonce();
        
        request.getSession().setAttribute("oidc_state", state.getValue());
        request.getSession().setAttribute("oidc_nonce", nonce.getValue());
        request.getSession().setAttribute("oidc_provider", providerName);

        // Build the OIDC Authorization URL using response_type=code (Secure Authorization Code Flow)
        String redirectUri = request.getRequestURL().toString().replace("/login/" + providerName, "/connect");
        
        String finalUrl = authEndpoint.toString() + 
            "?client_id=" + config.getClientID().getValue() +
            "&response_type=code" +
            "&scope=openid email profile" +
            "&redirect_uri=" + redirectUri +
            "&state=" + state.getValue() +
            "&nonce=" + nonce.getValue();

        return Response.seeOther(new URI(finalUrl)).build();
    }
    
    @GET
    @Path("connect")
    public String connect(
            @QueryParam("code") String codeValue,
            @QueryParam("state") String stateValue,
            @Context HttpServletRequest request) {
        
        try {
            String sessionState = (String) request.getSession().getAttribute("oidc_state");
            String sessionNonce = (String) request.getSession().getAttribute("oidc_nonce");
            String providerName = (String) request.getSession().getAttribute("oidc_provider");

            if (sessionState == null || !sessionState.equals(stateValue)) {
                return "Invalid state (potential CSRF attack)";
            }

            if (codeValue == null) {
                return "Authorization code missing";
            }

            // 1. Exchange the code for tokens
            String redirectUri = request.getRequestURL().toString();
            OIDCTokens tokens = oidcService.tokenExchange(
                providerName, 
                new AuthorizationCode(codeValue), 
                new URI(redirectUri)
            );

            // 2. Securely validate the ID Token
            IDTokenClaimsSet claims = oidcService.validateIdToken(
                providerName, 
                tokens.getIDToken(), 
                new Nonce(sessionNonce)
            );

            // 3. Successfully authenticated!
            // Here you would typically create a session for the user in your application
            return "Successfully logged in as: " + claims.getStringClaim("email") + " (" + claims.getSubject() + ")";
            
        } catch (Exception e) {
            return "Authentication failed: " + e.getMessage();
        }
    }
}
