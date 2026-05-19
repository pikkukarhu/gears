/**
 * 
 */
package fi.pa.gears.model;

import java.util.Date;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;

/**
 * @author mma
 *
 *	
 * User as template to return for creating new GearPair
 */
public class GearTemplate {

	public class Identity {
		Ref<Status> status;
		public Date cretated = new Date();
		public Date modified;
		
		public User create;
		
		public Identity() {
			this.status = Ref.create( ObjectifyService.ofy().load().type(Status.class).filter("code", "CREATED").first().now());
		}
	};
	
	public class Gear {
		public class Geometry {
			public GearType type;
			public double alpha;
		};
	
		public Identity identity;
		public Geometry geometry;
		public Profile profile;
		
		public Gear(GearType type, Profile profile) {
			this.geometry = new Geometry();
			this.geometry.alpha = profile.getAlpha();
			this.geometry.type = type;
			this.identity = new Identity();
			this.profile = profile;
			
		}
	}
	
		
	public GearType type;
	public Identity identity;
	
	public Gear firstGear;
	public Gear secondGear;
	
	public GearTemplate(GearType type, Profile profile) {
		this.type = type;
		this.identity = new Identity();
		
		this.firstGear = new Gear(type, profile);
		this.secondGear = new Gear(type, profile);
	}
}
