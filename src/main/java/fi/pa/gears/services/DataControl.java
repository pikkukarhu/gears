/**
 * 
 */
package fi.pa.gears.services;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.objectify.ObjectifyService;

import fi.pa.gears.model.Gear;
import fi.pa.gears.model.GearPair;
import fi.pa.gears.model.GearTemplate;
import fi.pa.gears.model.GearType;
import fi.pa.gears.model.Profile;
import fi.pa.gears.model.Status;
import fi.pa.gears.model.exception.GearCompatibilityException;

/**
 * @author mma
 *
 */
@Path("/data")
public class DataControl {
	
	static {
		ObjectifyService.register(Gear.class);
		ObjectifyService.register(GearPair.class);
		ObjectifyService.register(Status.class);
		
		System.out.println("Class initialized");
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("gears/pair/{lang}/list")
	public String listGearPairs(@PathParam("lang") String langCode) throws JsonProcessingException {

		List<GearPair> list = ObjectifyService.ofy().load().type(GearPair.class).list();
		return  (new ObjectMapper()).writeValueAsString(list);
	}

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("gears/pair/{lang}/{query}/list")
	public String listGearPairs(@PathParam("lang") String langCode, @PathParam("query") String query) {
		
		return "";
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("gears/pair/{lang}/new")
	public String createGearPair(@PathParam("lang") String langCode) throws JsonProcessingException, GearCompatibilityException {
		return  (new ObjectMapper()).writeValueAsString(new GearTemplate(GearType.spur_external, new Profile()));
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("gears/pair/save")
	public String saveGearPair(@JsonProperty("content") String content) throws JsonParseException, JsonMappingException, IOException {
		
		GearPair gp = (new ObjectMapper()).readValue(content, GearPair.class);
		ObjectifyService.ofy().save().entities(gp);
		return content;

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("gears/pair/{lang}/{id}")
	public String getGearPair(@PathParam("lang") String langCode, @PathParam("id") Long id) throws GearCompatibilityException, JsonProcessingException {

		GearPair gp = ObjectifyService.ofy().load().type(GearPair.class).id(id).now();
		return  (new ObjectMapper()).writeValueAsString(gp);
	}

	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("admin/status/{lang}/list")
	public String getStatusList(@PathParam("lang") String langCode) throws JsonProcessingException {
		List<Status> statlist = ObjectifyService.ofy().load().type(Status.class).list();
		
		return  (new ObjectMapper()).writeValueAsString(statlist);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("admin/status/list/delete")
	public String deleteStatusList(@JsonProperty("content") String content) throws JsonParseException, JsonMappingException, IOException {
		
		System.out.println("CONTENT = " + content);
		List<Status> statList = (new ObjectMapper()).readValue(content, new TypeReference<List<Status>>(){});
		ObjectifyService.ofy().delete().entities(statList).now();  
		
		return content;
	}
	
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("admin/status/list")
	public String addStatusList(@JsonProperty("content") String content) throws JsonParseException, JsonMappingException, IOException {
		
		System.out.println("PUT : " + content);
		List<Status> statList = (new ObjectMapper()).readValue(content, new TypeReference<List<Status>>(){});
		ObjectifyService.ofy().save().entities(statList);
		return content;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("admin/status/list")
	public String updateStatusList(@JsonProperty("content") String content) throws JsonParseException, JsonMappingException, IOException {
		
		System.out.println("POST : " + content);
		List<Status> statList = (new ObjectMapper()).readValue(content, new TypeReference<List<Status>>(){});
		ObjectifyService.ofy().save().entities(statList);
		return content;
	}

}
