/**
 * 
 */
package fi.pa.gears.model;

/**
 * @author mma
 *
 */
public class Lists {
	
	public enum Type {
		internal,
		external
	};
	
	public enum Cut {
		spur,
		helical
	};
	
	public class Profile {
		
		public final String name;
		public final double alpha; 
		
		public Profile(String name, double alpha) {
			this.name = name;
			this.alpha = alpha;
		}
	}
	
	public static final Object[][] modulos = {
		{"1", 0.5,  "2", 0.55},
		{"1", 0.6,  "2", 0.6},
		{"1", 0.8,  "2", 0.9},
		{"1", 1,    "2", 1.125},
		{"1", 1.25, "2", 1.375},
		{"1", 1.5,  "2", 1.75},
		{"1", 2,    "2", 2.25},
		{"1", 2.5,  "2", 2.75},
		{"1", 3,    "2", 3.5},
		{"1", 4,    "2", 4.5},
		{"1", 5,    "2", 5.5},
		{"1", 6,    "2", 7},
		{"1", 8,    "2", 9 },
		{"1", 10,   "2", 11},
		{"1", 12,   "2", 14},
		{"1", 16,   "2", 18},
		{"1", 20,   "2", 22},
		{"1", 25,   "2", 28},
		{"1", 32,   "2", 36},
		{"1", 40,   "2", 45},
		{"1", 50}};
}
