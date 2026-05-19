/**
 * 
 */
package fi.pa.gears.model;


import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

import fi.pa.gears.model.exception.GearCompatibilityException;

/**
 * @author mma
 *
 */
@Entity
public class GearPair {
	private static int GEARPAIR_CODE_POSTFIX;
	
	@Id private Long id;
	
	@Index private GearType type;
	private Identity identity;
	@Load private Ref<Gear> g1;
	@Load private Ref<Gear> g2;
	
	
	@SuppressWarnings("unused")
	private GearPair() {}
	
	/**
	 * 
	 * @param g1
	 * @param g2
	 * @throws GearCompatibilityException
	 */
	public GearPair(Gear g1, Gear g2) throws GearCompatibilityException {
		
		if (!g1.isCompatible(g2)) {
			throw new GearCompatibilityException();
		}
		
		this.g1 = Ref.create(g1); // ObjectifyService.ofy().load().type(Status.class).filter("code", "CREATED").first().now());g1;
		this.g2 = Ref.create(g2);
		
		String prefix = "";
		
		switch (g1.getType()) {
		case helical_external:
			if (g2.getType() == GearType.helical_external) {
				this.type = GearType.helical_external;
			}
			else {
				this.type = GearType.helical_internal;
			}
			break;
		case helical_internal:
			this.type = GearType.helical_internal;
			break;
		case spur_external:
			if (g2.getType() == GearType.spur_external) {
				this.type = GearType.spur_external;
			}
			else {
				this.type = GearType.spur_internal;
			}

			break;
		case spur_internal:
			this.type = GearType.spur_internal;
			break;
		default:
			break;
		
		}
		
		switch (this.type) {
		case helical_external:
			prefix = "PAIR-HELEXT";
			break;
		case helical_internal:
			prefix = "PAIR-HELINT";
			break;
		case spur_external:
			prefix = "PAIR-SPUREXT";
			break;
		case spur_internal:
			prefix = "PAIR-SPURINT";
			break;
		default:
			break;
		
		}
		
		this.identity = new Identity(String.format(prefix + "%d08", GEARPAIR_CODE_POSTFIX++));
	}

	public GearType getType() {
		return this.type;
	}
	
	public void setIdentity(Identity identity) {
		this.identity = identity;
	}
	
	public Identity getIdentity() {
		return this.identity;
	}

	public void setFirstGear(Gear gear) {
		ObjectifyService.ofy().save().entity(gear);

		this.g1 = Ref.create(gear); 
	}
	
	
	public Gear getFirstGear() {
		return this.g1.get();
	}
	
	public void setSecondGear(Gear gear) {
		ObjectifyService.ofy().save().entity(gear);
		this.g2 = Ref.create(gear); 
	}

	
	public Gear getSecondGear() {
		return this.g2.get();
	}
	
	public Long getId() {
		return this.id;
	}
}
