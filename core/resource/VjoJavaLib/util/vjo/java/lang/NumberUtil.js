vjo.ctype('vjo.java.lang.NumberUtil') //< public abstract
.needs(['vjo.java.lang.Byte', 'vjo.java.lang.Double', 'vjo.java.lang.Float',
        'vjo.java.lang.Integer', 'vjo.java.lang.Long', 'vjo.java.lang.Short',
        'vjo.java.lang.NumberFormatException', 'vjo.java.lang.Util', 'vjo.java.lang.reflect.Array'])
.props({
	patterns: undefined, //< Object[]
    //> public byte parseByte(String s, int radix)
    parseByte:function(s, radix){
        if(!radix) radix = 10;
        var value =  this.parseInt(s, radix, this.vj$.Byte.MIN_VALUE, this.vj$.Byte.MAX_VALUE);
        return this.vj$.Util.cast(value, 'byte');
    },
    //> public short parseShort(String s, int radix)
    parseShort:function(s, radix){
        if(!radix) radix = 10;
        var value =  this.parseInt(s, radix, this.vj$.Short.MIN_VALUE, this.vj$.Short.MAX_VALUE);
        return this.vj$.Util.cast(value, 'short');
    },
    //> public int parseInteger(String s, int radix)
    parseInteger:function(s, radix){
        if(!radix) radix = 10;
        var value =  this.parseInt(s, radix, this.vj$.Integer.MIN_VALUE, this.vj$.Integer.MAX_VALUE);
        return this.vj$.Util.cast(value, 'int');
    },
    //> public long parseLong(String s, int radix)
    parseLong:function(s, radix){
        if(!radix) radix = 10;
        var value =  this.parseInt(s, radix, this.vj$.Long.MIN_VALUE, this.vj$.Long.MAX_VALUE);
        return this.vj$.Util.cast(value, 'long');
    },
    //> private int parseInt(String s, int radix, int mix, int max)
    parseInt:function(s, radix, min, max){
        this.validateInt(s, radix);
        var value = parseInt(s, radix);
        this.assertScope(value, min, max);
        return value;
    },
    //> private void validateInt(String s, int radix)
    validateInt:function(s, radix){
        if (s === null) {
            throw new this.vj$.NumberFormatException("null");
        }

        if (radix < 2) {
            throw new this.vj$.NumberFormatException("radix " + radix +
			    " less than Character.MIN_RADIX");
        }

        if (radix > 36) {
            throw new this.vj$.NumberFormatException("radix " + radix +
			    " greater than Character.MAX_RADIX");
        }

        var pattern = this.initialize(radix);
        if(!pattern.test(s)) {
	        throw new this.vj$.NumberFormatException('value(' + s + ') is not well-formed!');
        }
    },
    //> private void assertScope(int value, int mix, int max)
    assertScope:function(value, min, max){
        if(value < min || value > max) {
        	throw new this.vj$.NumberFormatException('value(' + value + ') out of scope!');
        }
    },
    //> private void initialize(int radix)
    initialize:function(radix){
        var index = radix-2;
        if(!this.patterns[index]) {
            var pattern = undefined;
            switch(index) {
            case 0: pattern = /^[-+]?(0|1)+$/; break;
            case 1: pattern = /^[-+]?[0-2]+$/; break;
            case 2: pattern = /^[-+]?[0-3]+$/; break;
            case 3: pattern = /^[-+]?[0-4]+$/; break;
            case 4: pattern = /^[-+]?[0-5]+$/; break;
            case 5: pattern = /^[-+]?[0-6]+$/; break;
            case 6: pattern = /^[-+]?[0-7]+$/; break;
            case 7: pattern = /^[-+]?[0-8]+$/; break;
            case 8: pattern = /^[-+]?[0-9]+$/; break;
            case 9: pattern = /^[-+]?[0-9aA]+$/; break;
            case 10: pattern = /^[-+]?[0-9abAB]+$/; break;
            case 11: pattern = /^[-+]?[0-9a-cA-C]+$/; break;
            case 12: pattern = /^[-+]?[0-9a-dA-D]+$/; break;
            case 13: pattern = /^[-+]?[0-9a-eA-E]+$/; break;
            case 14: pattern = /^[-+]?[0-9a-fA-F]+$/; break;
            case 15: pattern = /^[-+]?[0-9a-gA-G]+$/; break;
            case 16: pattern = /^[-+]?[0-9a-hA-H]+$/; break;
            case 17: pattern = /^[-+]?[0-9a-iA-I]+$/; break;
            case 18: pattern = /^[-+]?[0-9a-jA-J]+$/; break;
            case 19: pattern = /^[-+]?[0-9a-kA-K]+$/; break;
            case 20: pattern = /^[-+]?[0-9a-lA-L]+$/; break;
            case 21: pattern = /^[-+]?[0-9a-mA-M]+$/; break;
            case 22: pattern = /^[-+]?[0-9a-nA-N]+$/; break;
            case 23: pattern = /^[-+]?[0-9a-oA-O]+$/; break;
            case 24: pattern = /^[-+]?[0-9a-pA-P]+$/; break;
            case 25: pattern = /^[-+]?[0-9a-qA-Q]+$/; break;
            case 26: pattern = /^[-+]?[0-9a-rA-R]+$/; break;
            case 27: pattern = /^[-+]?[0-9a-sA-S]+$/; break;
            case 28: pattern = /^[-+]?[0-9a-tA-T]+$/; break;
            case 29: pattern = /^[-+]?[0-9a-uA-U]+$/; break;
            case 30: pattern = /^[-+]?[0-9a-vA-V]+$/; break;
            case 31: pattern = /^[-+]?[0-9a-wA-W]+$/; break;
            case 32: pattern = /^[-+]?[0-9a-xA-X]+$/; break;
            case 33: pattern = /^[-+]?[0-9a-yA-Y]+$/; break;
            case 34: pattern = /^[-+]?[0-9a-zA-Z]+$/; break;
            }
            this.patterns[index] = pattern;
        }
        return this.patterns[index];
    } 
})
.inits(function() {
    this.vj$.NumberUtil.patterns = this.vj$.Array.newInstance(null, 35);
})
.endType();