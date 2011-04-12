vjo.ctype("vjo.java.lang.MathUtil")
.needs(['vjo.java.lang.Short', 'vjo.java.lang.Integer'])
.props({
	dec2Hex: function(x) {
		x = Number(x);
		
		if (isNaN(x)) {
			return Number.NaN.toString();
		}
		if (x === Number.POSITIVE_INFINITY) {
			return Number.POSITIVE_INFINITY.toString();
		}
		if (x === Number.NEGATIVE_INFINITY) {
			return Number.NEGATIVE_INFINITY.toString();
		}
		
		return "0x" + x.toString(16);
	},
	/*
	 * Convert to a 16-bit integer (short). 
	 */
	toInt16: function(x) {
		x = Number(x);

		if ( isNaN(x)
				|| x == 0
				|| x == Number.POSITIVE_INFINITY
				|| x == Number.NEGATIVE_INFINITY )
		{
			return 0;
		}
		
		if (x >= this.vj$.Short.MAX_VALUE) {
			return this.vj$.Short.MAX_VALUE;
		}
		
		if (x <= this.vj$.Short.MIN_VALUE) {
			return this.vj$.Short.MIN_VALUE;
		}
		
		var sign = (x < 0) ? -1 : 1;
		x = sign * Math.floor(Math.abs(x));
		x = x % Math.pow(2,16);
		x = (x > -65536 && x < 0) ? 65536 + x : x;
		
		return x;
	},
	/*
	 * Convert to a signed 32-bit integer.
	 */
	toInt32: function(x) {
		x = Number(x);
		var sign = (x < 0) ? -1 : 1;
		
		if (Math.abs(x) == 0 || Math.abs(x) == Number.POSITIVE_INFINITY) {
			return 0;
		}
		
		if (x >= this.vj$.Integer.MAX_VALUE) {
			return this.vj$.Integer.MAX_VALUE;
		}

		if (x <= this.vj$.Integer.MIN_VALUE) {
			return this.vj$.Integer.MIN_VALUE;
		}

		x = (sign * Math.floor( Math.abs(x) )) % Math.pow(2,32);
		
		if (sign == -1) {
			x = (x < -Math.pow(2,31)) ? x + Math.pow(2,32) : x;
		}
		else {
			x = (x >= Math.pow(2,31)) ? x - Math.pow(2,32) : x;
		}
		
		return x;
	},
	/*
	 * Convert to an unsigned 32-bit integer.
	 */
	toUint32: function(x) {
		x = Number(x);
		var sign = (x < 0) ? -1 : 1;
		
		if (Math.abs(x) == 0 || Math.abs(x) == Number.POSITIVE_INFINITY) {
			return 0;
		}
		
		x = sign * Math.floor( Math.abs(x) )
		x = x % Math.pow(2,32);
		
		if (x < 0) {
			x += Math.pow(2,32);
		}
		
		return x;
	},
	/*
	 * Log 10
	 */
	log10: function(x) {
		return Math.LOG10E * Math.log(x);
	},
	/*
	 * Cubic root
	 */
	cbrt: function(x) {
		x = Number(x);
		
		if (isNaN(x)) {
			return Number.NaN;
		}
		if (x === Number.POSITIVE_INFINITY) {
			return Number.POSITIVE_INFINITY;
		}
		if (x === Number.NEGATIVE_INFINITY) {
			return Number.NEGATIVE_INFINITY;
		}
		
		var result = "";
		// JS doesn't seem to handle negatives with Math.pow, so we need to compensate.
		if (x < 0) {
			result += "-"
		}
		x = Math.abs(x);
		result += Math.pow(x, 1/3);

		return Number(result);
	},
	/*
	 * Signum
	 */
	signum: function(x) {
		x = Number(x);
		
		if (isNaN(x)) {
			return Number.NaN;
		}

		return (x > 0) ? 1 : (x < 0) ? -1 : x;
	},
	/*
	 * Hyperbolic sine
	 */
	sinh: function(x) {
		x = Number(x);
		
		if (isNaN(x)) {
			return Number.NaN;
		}
		
		if (!isFinite(x)) {
			// already handled NaN case
			return (x === Number.POSITIVE_INFINITY) ?
					Number.POSITIVE_INFINITY :
					Number.NEGATIVE_INFINITY;
		}
		
		return ( (Math.exp(x) - Math.exp(-x)) / 2 );
	},
	/*
	 * Hyperbolic cosine
	 */
	cosh: function(x) {
		x = Number(x);
	
		if (isNaN(x)) {
			return Number.NaN;
		}
		
		if (!isFinite(x)) {
			// already handled NaN case
			// have to return positive infinity for infinite case -- see Math.cosh Java Docs
			return Number.POSITIVE_INFINITY;
		}
		
		if (x === 0) {
			return 1;
		}
		
		return ( (Math.exp(x) + Math.exp(-x)) / 2 );
	},
	/*
	 * Hyperbolic tangent
	 */
	tanh: function(x) {
		x = Number(x);
	
		if (isNaN(x)) {
			return Number.NaN;
		}
		
		if (0 === x) {
			return 0;
		}
		
		if (!isFinite(x)) {
			// already handled NaN case
			return (x === Number.POSITIVE_INFINITY) ? 1 : -1;
		}
		
		return ( this.sinh(x) / this.cosh(x) );

	},
	/*
	 * Hypotenuse
	 */
	hypot: function(x, y) {
		x = Number(x);
		y = Number(y);
		
		// if either argument is infinite, return positive infinity.
		// make sure it isn't NaN because isFinite returns false for NaN too.
		if ( !isFinite(x) && !isNaN(x) ) {
			return Number.POSITIVE_INFINITY;
		}
		if ( !isFinite(y) && !isNaN(y) ) {
			return Number.POSITIVE_INFINITY;
		}

		// if either argument is NaN and neither argument is infinite, then the result is NaN.
		if (isNaN(x) || isNaN(y)) {
			return Number.NaN;
		}

		return Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) );
	},
	expm1: function(x) {
		x = Number(x);
		
		if (isNaN(x)) {
			return Number.NaN;
		}
		
		if (!isFinite(x)) {
			// already took care of NaN
			return (x === Number.POSITIVE_INFINITY) ? Number.POSITIVE_INFINITY : -1.0;
		}
		
		if (x === 0) {
			return 0;
		}
		
		return Math.exp(x) - 1;
	},
	log1p: function(x) {
		x = Number(x);
		
		if (isNaN(x) || x < -1) {
			return Number.NaN;
		}
		
		if (x === Number.POSITIVE_INFINITY) {
			return Number.POSITIVE_INFINITY;
		}
		
		if (x === -1) {
			return Number.NEGATIVE_INFINITY;
		}
		
		if (x === 0) {
			return 0;
		}
		
		return Math.log(x + 1);
	},
	rint: function(x) {
		x = Number(x);
		
		if (isNaN(x) ||
				x === Number.POSITIVE_INFINITY ||
				x === Number.NEGATIVE_INFINITY)
		{
			return x;
		}
		
		var orig = x;
		x = this.toInt32(x);

		if (orig === x) {
			return orig;
		}
		
		var posVal = Math.ceil(orig);
		var negVal = Math.floor(orig);
		var posDiff = posVal - orig;
		var negDiff = orig - negVal;

		// if ceil value is closer to original than floor value, return ceil, otherwise floor.
		if (posDiff < negDiff) {
			return posVal;
		}
		else if (negDiff < posDiff) {
			return negVal;
		}
		
		// if ceil and floor values are equally close, return the one that is an even value.
		if (posVal % 2 === 0) {
			return posVal;
		}
		if (negVal % 2 === 0) {
			return negVal;
		}
	
		// Default catch
		return x;
	}
})
.endType();