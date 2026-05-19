/**
 * 
 */
package fi.pa.gears.model;

import java.util.Date;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonProperty;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

/**
 * @author mma
 *
 */

public class Identity {

	@Id private Long id;
	
	@Index private String code;
	private String version;
	@Index private String desc1Key;
	@Index private String desc2Key;
	
	@Index private User create;
	@Load private Ref<Status> status;
	private Date created;
	private Date modified;
	
	@SuppressWarnings("unused")
	public Identity() {}
	
	public Identity(String code) {
		this.code = code;
		
		this.created = new Date();
		this.modified = new Date();
		
		this.status = Ref.create( ObjectifyService.ofy().load().type(Status.class).filter("code", "CREATED").first().now());
				
	}
	
	/**
	 * @return the desc1Key
	 */
	@JsonProperty("desc1")
	public String getDesc1Key() {
		return desc1Key;
	}
	/**
	 * @param desc1Key the desc1Key to set
	 */
	@JsonProperty("desc1")
	public void setDesc1Key(String desc1Key) {
		this.desc1Key = desc1Key;
	}
	/**
	 * @return the desc2Key
	 */
	@JsonProperty("desc2")
	public String getDesc2Key() {
		return desc2Key;
	}
	/**
	 * @param desc2Key the desc2Key to set
	 */
	@JsonProperty("desc2")
	public void setDesc2Key(String desc2Key) {
		this.desc2Key = desc2Key;
	}
	/**
	 * @return the status
	 */
	@JsonProperty("status")
	public Status getStatus() {
		return status.get();
	}
	/**
	 * @param status the status to set
	 */
	@JsonProperty("status")
	public void setStatus(Status status) {
		this.status = Ref.create(status); //status;
	}
	/**
	 * @return the modified
	 */
	@JsonProperty("modified")
	public Date getModified() {
		return modified;
	}
	/**
	 * @param modified the modified to set
	 */
	@JsonProperty("modified")
	public void setModified(Date modified) {
		this.modified = modified;
	}
	/**
	 * @return the code
	 */
	@JsonProperty("code")
	public String getCode() {
		return code;
	}
	/**
	 * @return the version
	 */
	@JsonProperty("version")
	public String getVersion() {
		return version;
	}
	/**
	 * @return the create
	 */
	@JsonProperty("create")
	public User getCreate() {
		return create;
	}
	/**
	 * @return the created
	 */
	@JsonProperty("create")
	public Date getCreated() {
		return created;
	}
	

	
}
