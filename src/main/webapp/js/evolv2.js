/**
 * 
 */
/*
 * 
 * Lataa GMath.js ennen evelvent.js scriptiä
 */

var Evolvent = {
	calculate : function(g1, g2) {
	        
		var aRad = GMath.degToRad(g1.alpha) // * Math.PI  / 180.0; // TODO Tsekkaa onko oikein!!!
		console.log('ARAD: ' + aRad);
			
		g1.D  = g1.m * g1.Z;
		g1.Db = (g1.m * g1.Z) * Math.cos(aRad);	
		g1.p  = g1.m * Math.PI;
		g1.hf = g1.m * (1.25 - g1.x); 
		g1.Df = g1.D * g1.hf;
	        
		if (typeof g2 === 'undefined') {
			g1.a   =  GMath.getUndefined();
			g1.alphaW = GMath.getUndefined();
			g1.aw  = GMath.getUndefined();
			g1.delta_ha = GMath.getUndefined();
			g1.h   =  GMath.getUndefined();
			g1.ha  = GMath.getUndefined();
			g1.Da  = GMath.getUndefined();
		}
		else { 
			g1.a = g1.m * (g1.Z + g2.Z) / 2.0;
	        
			var guess = aRad;
			g1.alphaW =
				function(G1_, G2_) {			
					var alphaW = GMath.newton (
						function(alfa, x1, x2, Z1, Z2) {
							var invAW = GMath.inv(alfa) - 2 * (x1 + x2) * Math.tan(alfa) / (Z1 + Z2);
							return function(x) { 
								return GMath.inv(x) - invAW; 
							};
						} (guess, G1_.x, G2_.x, G1_.Z, G2_.Z), guess);
							
					return  GMath.radToDeg(alphaW); // 180.0 * alphaW / Math.PI;
				} (g1, g2);
	                
			var awRad = GMath.degToRad(g1.alphaW); // ()  * Math.PI / 180.0;
			g1.aw = g1.a * Math.cos(aRad) / (Math.cos(awRad));
			g1.delta_ha = g1.a * Math.cos(aRad) / (Math.cos(awRad));
			g1.h =  2.25 * g1.m - g1.delta_ha; 
			g1.ha = g1.m * (1 + g1.x) - g1.delta_ha;
			g1.Da = g1.D + 2 * g1.ha;
		};
		
		console.log(JSON.stringify(g1));
	},

	modulos : 
		[
			{"1" : 0.5,  "2" : 0.55},
			{"1" : 0.6,  "2" : 0.6},
			{"1" : 0.8,  "2" : 0.9},
			{"1" : 1,    "2" : 1.125},
			{"1" : 1.25, "2" : 1.375},
			{"1" : 1.5,  "2" : 1.75},
			{"1" : 2,    "2" : 2.25},
			{"1" : 2.5,  "2" : 2.75},
			{"1" : 3,    "2" : 3.5},
			{"1" : 4,    "2" : 4.5},
			{"1" : 5,    "2" : 5.5},
			{"1" : 6,    "2" : 7},
			{"1" : 8,    "2" : 9 },
			{"1" : 10,   "2" : 11},
			{"1" : 12,   "2" : 14},
			{"1" : 16,   "2" : 18},
			{"1" : 20,   "2" : 22},
			{"1" : 25,   "2" : 28},
			{"1" : 32,   "2" : 36},
			{"1" : 40,   "2" : 45},
			{"1" : 50}]
			
/*	createGearPair = function(type) {
		var p = { 
				"identity": {
					"status" : {}	
				},
				"firstGear": {
					"identity": {}
				
		};
		p.type = type; */
		

	    
};

