/**
 * 
 */
package fi.pa.gears.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * @author mma
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Status {

	@JsonProperty
	@Id private Long id;
	@Index private String code;
	private String description;
	private boolean locked = false;
		
	/**
	 * @return the descKey
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param descKey the descKey to set
	 */
	public void setDescriptiony(String description) {
		this.description = description;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * @return the locked
	 */
	public boolean isLocked() {
		return this.locked;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getId() {
		return this.id;
	}
}
