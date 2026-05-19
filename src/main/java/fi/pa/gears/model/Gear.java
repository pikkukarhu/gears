package fi.pa.gears.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Gear {
	private static int GEAR_CODE_POSTFIX = 0;
	private static final double MIN_ANGLE = 0.00001;
	
	@Id private Long id = null;
	
	private Identity identity;
//	private ArrayList<GearPair> whereUsed = new ArrayList<>();
	private Geometry geometry;
	@Index private GearType type;
	private Profile profile;
	
	@SuppressWarnings("unused")
	private Gear() {}
	
	/**
	 * 
	 * @param type
	 * @param profile
	 * @param m
	 * @param Z
	 * @param x
	 * @param b
	 * @param beta
	 */
	public Gear(GearType type, Profile profile, double m, double Z, double x, double b, double beta) {
		
		// Korjaa tyyppi jos vinouskulma on tarkkuuden rajoissa nolla
		if (beta < MIN_ANGLE) {
			switch (type) {
			case helical_external:
				type = GearType.spur_external;
				break;
			case helical_internal:
				type =  GearType.spur_internal;
				break;
			default:
				break;
				
			}
		}
		// Luo identiteetti
		String prefix = "";
		switch (type) {
		case helical_external:
			prefix = "HELEXT";
			break;
		case helical_internal:
			prefix = "HELINT";
			break;
		case spur_external:
			prefix = "SPUREXT";
			break;
		case spur_internal:
			prefix = "SPURINT";
			break;
		default:
			break;
		
		}
		this.type = type;
		this.profile = profile;
		
		this.identity = new Identity(String.format(prefix + "%d", GEAR_CODE_POSTFIX++));
		this.geometry = new Geometry(type, profile, m, Z, x, b, beta);
	}
	
	/**
	 * 
	 * @param type
	 * @param profile
	 * @param m
	 * @param Z
	 * @param x
	 * @param b
	 */
	public Gear(GearType type, Profile profile, double m, double Z, double x, double b) {
		this(type, profile, m, Z, x, b, 0.0);
	}

	
	public Gear(GearType type, Profile profile) {
		this(type, profile, 0.0, 0, 0.0, 0.0);
	}

	/**
	 * 
	 * @return
	 */
	public GearType getType() {
		return this.type;
	}
	
	
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	/**
	 * 
	 * @return
	 */
	public Profile getProfile() {
		return this.profile;
	}
	
	
	public void setIdentity(Identity identity) {
		this.identity = identity;
	}
	
	/**
	 * 
	 * @return
	 */
	public Identity getIdentity() {
		return this.identity;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}
	
	/**
	 * 
	 * @return
	 */
	public Geometry getGeometry() {
		return this.geometry;
	}
	
	/**
	 * 
	 * @param g
	 * @return
	 */
	public boolean isCompatible(Gear g) {
		boolean ok = false;
		
		// TODO Oikea yhteensopivuusvertailu hammasyhtälöstä, sekä ulko että sisähammaspyörille
		
		if (this.profile.equals(g.profile)
				&& (this.geometry.m - g.geometry.m < MIN_ANGLE)) {
			
			switch (this.type) {
				case helical_external:
					if (g.type == GearType.helical_external) {
						ok = true;
					}
					else if (g.type  == GearType.helical_internal) {
						if (g.geometry.Z > this.geometry.Z) {
							ok = true;
						}
					}
					break;
				case helical_internal:
					if (g.type  == GearType.helical_external) {
						if (g.geometry.Z < this.geometry.Z) {
							ok = true;
						}
					}
					break;
				case spur_external:
					if (g.type == GearType.spur_external) {
						ok = true;
					}
					else if (g.type  == GearType.spur_internal) {
						if (g.geometry.Z > this.geometry.Z) {
							ok = true;
						}
					}
					break;
				case spur_internal:
					if (g.type  == GearType.spur_external) {
						if (g.geometry.Z < this.geometry.Z) {
							ok = true;
						}
					}
					break;
				default:
					break;
			
			}
		}
		return ok;
	}
	
//	/**
//	 * 
//	 * @return
//	 */
//	public Collection<GearPair> getWhereUsed() {
//		return this.whereUsed;
//	}
	
	/**
	 * 
	 * @param owner
	 */
	public void addUsingPair(GearPair owner) {
// FIXME		this.whereUsed.add(owner);
	}
	
	/**
	 * 
	 * @param owner
	 */
	public void removeUsingPair(GearPair owner) {
// FIXME		this.whereUsed.remove(owner);
	}
	
	
	/*
	 * 
	 */
	/**
	 * 
	 * @author mma
	 *
	 */
	public static class Geometry {
		
		public GearType type;

		public double alpha; // = 20.0;           //  Ryntökulma profiilista, yleensä 20 deg. TODO Tarkistetaan onko profiili, jos, otetaan profiilista
		public double m; // = initial.m;          //  Moduuli
		public double Z; // = initial.Z;          //  Hammasluku
		public double x; // = initial.x;          //  Profiilinsiirto
		public double b; // = initial.b;          //  Hammasleveys
		public double beta; // = initial.beta;    //  Vinouskulma
    
//		// Lasketut
		public double ha;         //  Pääkorkeus = function(G1, G2) { return G1.m * (1 + G1.x) - $scope.delta_ha(G1, G2); };
    
		@Ignore public double D;          //  Halkaisija = function(G) { return G.m * G.Z; };
		@Ignore public double Db;         //  Perushalkaisija = function(G) { return (G.m * G.Z) * Math.cos(G.alpha* (Math.PI / 180.0)) ; };
		@Ignore public double p;          //  Jako  = function(G) { return G.m * Math.PI; };
		@Ignore public double a;          //  Perusakseliväli = function(G1, G2) { return G1.m * (G1.Z + G2.Z) / 2.0; };
		@Ignore public double alphaW;      //  Ryntökulma vierintäpinnalla 
		@Ignore public double aw;         //  Akseliväli = function (G1, G2) { return $scope.a(G1, G2) * Math.cos(G1.alpha* (Math.PI / 180.0)) / (Math.cos($scope.alfaW(G1, G2) * Math.PI / 180.0)); };
		@Ignore public double delta_ha;   //  Pääkorkeuden lyhennys = function(G1, G2) { var a = G1.m * ((G1.Z + G2.Z) / 2 + G1.x + G2.x) - $scope.aw(G1, G2); return a <= 0.0 ? a : 0.0; };
		@Ignore public double h;          //  Hampaan korkeus = function(G1, G2) { return 2.25 * G1.m - $scope.delta_ha(G1, G2); };
		@Ignore public double hf;         //  Tyvikorkeus = function (G) { return G.m * (1.25 - G.x); };
		@Ignore public double Da;         //  Päähalkaisija = function (G1, G2) { return $scope.D(G1) + 2 * $scope.ha(G1, G2); };
		@Ignore public double Df;         //  Tyvihalkaisija = function (G) { return $scope.D(G) - 2 * $scope.hf(G);};
		
		
		@SuppressWarnings("unused")
		private Geometry() {}
		
		public Geometry(GearType type, Profile profile, double m, double Z, double x, double b, double beta) {
			this.type = type;
			this.alpha = profile.getAlpha();
			this.m = m;
			this.Z = Z;
			this.x = x;
			this.b = b;
			this.beta = beta;
		}
	}

}
