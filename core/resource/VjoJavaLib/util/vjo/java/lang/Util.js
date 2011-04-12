vjo.ctype("vjo.java.lang.Util")
.needs("vjo.java.lang.Number")
.props({
	MAX_FLOAT:3.4028235e+38,
	MAX_LONG:9223372036854775807,
	MAX_INT:2147483647,
	MAX_SHORT:32767,
	MAX_BYTE:127,
	MIN_FLOAT:1.4e-45,
	MIN_LONG:-9223372036854775808,
	MIN_INT:-2147483648,
	MIN_SHORT:-32768,
	MIN_BYTE:-128,    

	cast:function(n,s){
		var tp;
		if(s === "long") {
			return this.castToLong(n);
		} else if (s === "int") {
			return this.castToInt(n);
		}
		else if (s==="byte"){
			return this.castToByte(n);
		}
		else if (s==="short"){
			return this.castToShort(n);
		}
		else if (s==="double"){
			return this.castToDouble(n);
		}
		else if (s==="float"){
			return this.castToFloat(n);
		} else {
			return this.castToType(n,s);
		}
	},
	castToLong:function(n) {
		n = Number(n);
		if ( isNaN(n) ) {
			n = 0;
		}
		if (n >=0) {
			return Math.floor(n);
		} else if (n < 0) {
			return Math.ceil(n);
		}
	},
	castToInt:function(n) {
		return this.convert(n,0xffffffff,this.MAX_INT);
	},
	castToShort:function(n) {
		return this.convert(n,0xffff,this.MAX_SHORT);	
	},
	castToByte:function(n) {
		return this.convert(n,0xff,this.MAX_BYTE);	
	},	
	castToDouble:function(n){
		return n;
	},
	castToFloat:function(n){
		if (n > this.MAX_FLOAT) {
			return Number.POSITIVE_INFINITY;
		}
		// use -MAX_FLOAT because MIN_FLOAT is actually still a positive number close to zero.
		else if (n < -this.MAX_FLOAT) {
			return Number.NEGATIVE_INFINITY;
		}
		return n;
	},
	castToType: function(n,t) {
		var tp = vjo.getType(t);
		if (t.instanceOf && t.isInstance(n)){
			return n;
		}
		throw "Invalid Cast Exception " + n + "cannot be cast to type " + t;
	},
	/*
	 * Logic problem with this conversion to ints from Longs, Floats, and Doubles.
	 */
	convert:function(n,bits,max){
		var t;
		if (n>=0){
			t = Math.floor(n);
		} else {
			t = Math.ceil(n);
		}
			t = t&bits;
			if (t>max){
				t = t - (max+1)*2;
		}
		return t;
	},
	//TODO: implement
	isRealTime : function () {
		return 0;
	},
	doubleToRawLongBits : function () {
		return 0;
	}
})
.endType();