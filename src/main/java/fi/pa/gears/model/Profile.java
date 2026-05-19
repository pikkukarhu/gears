/**
 * 
 */
package fi.pa.gears.model;

/**
 * @author mma
 *
 */
public class Profile {

	private double alpha = 20; // Default 20deg.
	private String name = "DIN 867:1986";
	
	/**
	 * 
	 * @return
	 */
	public double getAlpha() {
		return this.alpha; 
	}
	
	/**
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Profile) {
			Profile p = (Profile) o;
			if (p.name.equals(this.name) && (p.alpha - this.alpha < 0.00001)) {
				return true;
			}
		}
		return false;
	}
}
