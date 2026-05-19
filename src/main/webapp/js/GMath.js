/**
 * 
 */

var GMath = {
	
	/*
	 * Ratkaisee funktion newtonin menetelmällä.
	 * Funktion derivaatta jossain pisteessä on sama kuin käyrän 
	 * tangentti ko. pisteessä. Joten likiarvon saa piirtämällä suoran funktion arvojen x - delta ja x + delta kautta
	 */
	newton : function  (f, x = 0.0, options = {
			delta : 0.001,
			epsilon : 0.000001,
			iterations : 100 }) {
		
		function derivate(f, x, d) {
			return ( f(x + d) - f(x - d)) / (d * 2.0);
		};

		for (var i = 0; i < options.iterations; ++i) {
			var a =  x - (f(x) / derivate(f, x, options.delta));
			console.log('x = ' + x + ', err = ' + Math.abs(x - a));
			if (Math.abs(x - a) <= options.epsilon) {
				return a;
			}
			x = a;
		}
		throw "Can not solve function " + f.toString() + " with aproximation " + x;
	},
	
	/*
	 * Kulman evolventti kun kulma radiaaneina
	 */
	inv : function  (a) {
	    return Math.tan(a) - a;
	},

	/*
	 * Kulman evolventti kun kulma annettu asteina
	 */
	degInv: function  (d) {
	    return this.radToDeg(this.inv(this.degToRad(d)));
	},
	
	
	degToRad : function(deg) {
		return (Math.PI * deg) / 180;
	},
	
	radToDeg : function(rad) {
		return (180 * rad) / Math.PI;
	},

	getUndefined : function() { return; }
};