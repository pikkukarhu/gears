/*
 * 
 * Lataa GMath.js ennen evelvent.js scriptiä
 */

function gear(initial = {}) {
    var TYPES = ["internal", "external"];
    var CUTS = ["spur", "helical"];
    
    var profiles = [{name : "", alpha : 20}];
    this.modulos = [
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
	{"1" : 50}]; 
    
    this.type    = initial.type;
    this.cut     = initial.cut;  
    this.profile = initial.profile;    //  Profiili;
 
    this.alpha = 20.0;           //  Ryntökulma profiilista, yleensä 20 deg. TODO Tarkistetaan onko profiili, jos, otetaan profiilista
    this.m = initial.m;          //  Moduuli
    this.Z = initial.Z;          //  Hammasluku
    this.x = initial.x;          //  Profiilinsiirto
    this.b = initial.b;          //  Hammasleveys
    this.beta = initial.beta;    //  Vinouskulma
    
    var aRad = this.alpha * Math.PI / 180;
    var awRad = aRad;
    
    this.D;          //  Halkaisija = function(G) { return G.m * G.Z; };
    this.Db;         //  Perushalkaisija = function(G) { return (G.m * G.Z) * Math.cos(G.alpha* (Math.PI / 180.0)) ; };
    this.p;          //  Jako  = function(G) { return G.m * Math.PI; };
    this.a;          //  Perusakseliväli = function(G1, G2) { return G1.m * (G1.Z + G2.Z) / 2.0; };
    this.alfaW;      //  Ryntökulma vierintäpinnalla 
    this.aw;         //  Akseliväli = function (G1, G2) { return $scope.a(G1, G2) * Math.cos(G1.alpha* (Math.PI / 180.0)) / (Math.cos($scope.alfaW(G1, G2) * Math.PI / 180.0)); };
    this.delta_ha;   //  Pääkorkeuden lyhennys = function(G1, G2) { var a = G1.m * ((G1.Z + G2.Z) / 2 + G1.x + G2.x) - $scope.aw(G1, G2); return a <= 0.0 ? a : 0.0; };
    this.h;          //  Hampaan korkeus = function(G1, G2) { return 2.25 * G1.m - $scope.delta_ha(G1, G2); };
    this.ha;         //  Pääkorkeus = function(G1, G2) { return G1.m * (1 + G1.x) - $scope.delta_ha(G1, G2); };
    this.hf;         //  Tyvikorkeus = function (G) { return G.m * (1.25 - G.x); };
    this.Da;         //  Päähalkaisija = function (G1, G2) { return $scope.D(G1) + 2 * $scope.ha(G1, G2); };
    this.Df;         //  Tyvihalkaisija = function (G) { return $scope.D(G) - 2 * $scope.hf(G);};
    
    this.getUndefined = function() { return; };
    
    this.calculate = function(G) {
        
        this.D  = this.m * this.Z;
        this.Db = (this.m * this.Z) * Math.cos(aRad);
        this.p  = this.m * Math.PI;
        this.hf = this.m * (1.25 - this.x); 
        this.Df = this.D * this.hf;
         
        if (typeof G === 'undefined') {
            this.a   =  this.getUndefined();
            this.alfaW = this.getUndefined();
            this.aw  = this.getUndefined();
            this.delta_ha = this.getUndefined();
            this.h   =  this.getUndefined();
            this.ha  = this.getUndefined();
            this.Da  = this.getUndefined();
        }
        else {
            this.a = this.m * (this.Z + G.Z) / 2.0;
            var guess = awRad;
            this.alfaW =
                function(G1, G2) {			
                    var alphaW = GMath.newton (
                        function(alfa, x1, x2, Z1, Z2) {
                            var invAW = GMath.inv(alfa) - 2 * (x1 + x2) * Math.tan(alfa) / (Z1 + Z2);
                            return function(x) { 
                            	return GMath.inv(x) - invAW; 
                            };
                        } (guess, G1.x, G2.x, G1.Z, G2.Z), 
                        guess);
						
                    	return 180 * alphaW / Math.PI;
            	} (this, G);
                
            awRad = this.alfaW * Math.PI / 180;
            this.aw = this.a * Math.cos(aRad) / (Math.cos(awRad));
            this.delta_ha = this.a * Math.cos(aRad) / (Math.cos(awRad));
            this.h =  2.25 * this.m - this.delta_ha; 
            this.ha = this.m * (1 + this.x) - this.delta_ha;
            this.Da = this.D + 2 * this.ha;
        };
        console.log(JSON.stringify(this));
    };
    
    console.log("Gear object created");
    this.calculate();
};

