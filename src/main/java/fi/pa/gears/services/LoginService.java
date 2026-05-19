/**
 * 
 */
package fi.pa.gears.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * @author mma
 *
 */

@Path("/oauth")
public class LoginService {
/*	
 * 
 * API_KEY=AIzaSyAxNRpVige-O_IzfiDWEsKASC2idTdF6_8
 * 
    <!--Client Id = 169671802224-hdrm7bsiaappf11o61n00mi4k98vfj5c.apps.googleusercontent.com-->
    <!--Redirect uri = https://martti89.ddns.net-->
    <link href="https://accounts.google.com/o/oauth2/v2/auth?client_id={client_id}&response_type=token&scope=openid&redirect_uri={redirect_uri}">Login with google</link>

*/
	public static final String CLIENT_ID = "80119940762-t8haqe16i92kml0cqv30e81vuq07a4cq.apps.googleusercontent.com";
	public static final String APPLICATION_NAME = "gears-210218";
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("login")
	public String login( @Context HttpServletRequest request) throws FileNotFoundException {
		
		/*
		 * Client ID	= 80119940762-t8haqe16i92kml0cqv30e81vuq07a4cq.apps.googleusercontent.com
Client secret	
_B2qVKFO__T07oNB7ZQ5wJsS
Creation date	
Feb 21, 2018, 6:44:32 PM

		 */
		  // Create a state token to prevent request forgery.
		  // Store it in the session for later validation.
		  String state = new BigInteger(130, new SecureRandom()).toString(32);
		  request.getSession().setAttribute("state", state);
		  
		  // Read index.html into memory, and set the client ID,
		  // token state, and application name in the HTML before serving it.
		  return new Scanner(new File("index.html"), "UTF-8")
		      .useDelimiter("\\A").next()
		      .replaceAll("[{]{2}\\s*CLIENT_ID\\s*[}]{2}", CLIENT_ID)
		      .replaceAll("[{]{2}\\s*STATE\\s*[}]{2}", state)
		      .replaceAll("[{]{2}\\s*APPLICATION_NAME\\s*[}]{2}",
		                  APPLICATION_NAME);
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("connect")
	public String connect( @Context HttpServletRequest request) {
		return "";
	}
}
