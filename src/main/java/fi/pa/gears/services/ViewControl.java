package fi.pa.gears.services;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;

/**
 * 
 * @author mma
 *
 */
@Path("/")
public class ViewControl {

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/")
	public String getIndexPage() throws IOException {
		return readFile("baseframe.html", "default");
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("{lang}")
	public String getIndexPage(@PathParam("lang") String langCode) throws IOException {
		String page = readFile("baseframe.html", langCode);
		return page;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("gears/{lang}/header")
	public String getGearHeader(@PathParam("lang") String langCode) throws IOException {
		String page = readFile("gears_header.html", langCode);
		return page;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("gears/{lang}/{query}/list")
	public String getGearList(@PathParam("lang") String langCode, @PathParam("query") String query) throws IOException {
		String page = readFile("gears_list.html", langCode);
		
		return page.replace("$QUERY$", query);
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("gears/{lang}/list")
	public String getGearList(@PathParam("lang") String langCode) throws IOException {
		String page = readFile("gears_list.html", langCode);
		
		return page.replace("$QUERY$", "");
	}

	
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("gears/{lang}/{code}/form")
	public String getGearForm(@PathParam("lang") String langCode, @PathParam("code") String code) throws IOException {
		String page = readFile("helicalmain.html", langCode);
		
		return page.replace("$QUERY$", "");

	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("admin/status/{lang}/list")
	public String getStatusList(@PathParam("lang") String langCode) throws IOException {
		String page = readFile("admin_status.html", langCode);
		return page;
	}
	
	private String readFile(String fileName, String langCode) throws IOException {
		File f = new File("view/" + langCode + "/" + fileName);
		if (!f.canRead()) {
			f =  new File("view/default/"  + fileName);
		}
		return  FileUtils.readFileToString(f, "UTF-8");
	}
}
