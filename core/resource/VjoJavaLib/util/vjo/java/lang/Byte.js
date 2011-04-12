vjo.ctype('vjo.java.lang.Byte') //< public final
.needs('vjo.java.lang.NumberFormatException')
.needs('vjo.java.lang.NumberUtil')
.props({
	MIN_VALUE : -128, //< public static final byte
	MAX_VALUE : 127, //< public static final byte
	SIZE:8, //< public static final int

	//> public static String toString(byte b)
	toString : function (b) {
		return b.toString(10);
	},
	
	ByteCache : vjo.ctype() //< private
	.props({
		//> static final Array 
		cache : vjo.createArray(null, -(-128) + 127 + 1)
	})
	.protos({
		//>public void constructs
		constructs : function () {
		
		}
	})
	.inits(function (){
		for(var i = 0; i < this.cache.length; i++) {
			this.cache[i] = new vjo.java.lang.Byte(i - 128);
		}
	})
	.endType(),
	
	//> public static Byte valueOf(byte b)
	//> public static Byte valueOf(String s)
	//> public static Byte valueOf(String s, int radix)
	valueOf : function (bOrs, radix) {
		
		if (typeof(bOrs) !== 'string') {
			var offset = 128;
			return this.ByteCache.cache[parseInt(bOrs) + offset];
		} else {
			if (!radix) radix = 10;
			return new this(this.parseByte(bOrs, radix));
		}
	},
	
	//>public static byte parseByte(String s, int radix)
	parseByte : function (s, radix) {
    return  this.vj$.NumberUtil.parseByte(s,radix);
	},
	
	//> public static Byte decode(String nm)
	decode : function (nm) {
	        var radix = 10;
	        var index = 0;
	        var negative = false;
	        var result;
	
	        // Handle minus sign, if present
	        if (nm.indexOf("-") === 0) {
	            negative = true;
	            index++;
	        }
	
		if (nm.indexOf("0x") === index || nm.indexOf("0X") === index) {
	            index += 2;
	            radix = 16;
		} else if (nm.indexOf("#") === index) {
		    index++;
	            radix = 16;
		} else if (nm.indexOf("0") === index && nm.length > 1 + index) {
		    index++;
	            radix = 8;
		}
	
	        if (nm.indexOf("-") === index)
			throw new this.vj$.NumberFormatException("Negative sign in wrong position");
	
	        try {
			result = this.valueOf(nm.substring(index), radix);
			result = negative ? new this(-result.byteValue()) : result;
	        } catch (e) {
			// If number is Byte.MIN_VALUE, we'll end up here. The next line
			// handles this case, and causes any genuine format error to be
			// rethrown.
			var constant;
			if (negative)
				constant = "-" + nm.substring(index);
			else
				constant = nm.substring(index);
			result = this.valueOf(constant, radix);
	        }
	        return result;
	}
})
.protos({
	//> final private byte value
	value:0,
    
	//> public constructs(byte b)
	//> public constructs(String s)
	constructs:function(bOrs){
		if (typeof(bOrs) === 'string' || bOrs === null) {
			this.value = this.vj$.Byte.parseByte(bOrs, 10);
		} else {
			this.value = bOrs;
		}
	},
    
	//> public byte byteValue()
	byteValue : function () {
		return this.value;
	},
	
	//> public short shortValue() 
	shortValue : function () {
		return this.value;
	},

	//> public int intValue() 
	intValue : function () {
		return parseInt(this.value);
	},

	//> public long longValue() 
	longValue : function () {
		return this.value;
	},

	//> public float floatValue() 
	floatValue : function () {
		return parseFloat(this.value);
    	},

	//> public double doubleValue() 
	doubleValue : function () {
		return parseFloat(this.value);
	},

	//> public String toString() 
	toString : function () {
		return '' + this.value;
	},
	
	//> public int hashCode() 
	hashCode : function () {
		return parseInt(this.value);
	},
	
	//>public boolean equals(Object obj) 
	equals : function (obj) {
		if (obj instanceof this.vj$.Byte) {
			return this.value === obj.byteValue();
		}
		return false;
	},
	
    //>public int compareTo(Byte anotherByte) 
    compareTo : function (anotherByte) {
        return this.value - anotherByte.value;
    }
	
})
.endType();