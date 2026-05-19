package fi.pa.gears.services.graphics;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 
 * @author mma
 *
 */
@Path("/svg")
public class SvgService {

	@POST
	@Path("/svg/drawing/wheel")
	@Produces(MediaType.APPLICATION_SVG_XML)
	public String getWheelDrawing() {
		
		
		
		return null;
	}
}
