vjo.ctype('vjo.java.lang.Short') //< public final
.needs(['vjo.java.lang.Integer','vjo.java.lang.NumberFormatException',
    'vjo.java.lang.Util','vjo.java.lang.StringUtil','vjo.java.lang.NumberUtil'])
.needs('vjo.java.lang.StringFactory','')
.inherits('vjo.java.lang.Number')
.satisfies('vjo.java.lang.Comparable<Short>')
.props({
    MIN_VALUE:-32768, //< public final short
    MAX_VALUE:32767, //< public final short
    SIZE:16, //< public final int
    ShortCache:vjo.ctype() //< private
    .props({
        cache:null //< final Short[]
    })
    .protos({
        //> private constructs()
        constructs:function(){
        }
    })
    .inits(function(){
        this.vj$.ShortCache.cache=vjo.createArray(null,  -(-128)+127+1);
        {
            for (var i=0;i<this.vj$.ShortCache.cache.length;i++){
                this.cache[i]=new this.vj$.Short(this.vj$.Util.cast((i-128),'short'));
            }
        }
    })
    .endType(),
    //> public String toString(short s)
    toString:function(s){
        return this.vj$.Integer.toString(this.vj$.Util.cast(s,'int'),10);
    },
    //> public short parseShort(String s)
    //> public short parseShort(String s,int radix)
    parseShort:function(s){
        if(arguments.length===1){
            if(arguments[0] instanceof String || typeof arguments[0]=="string"){
                return this.vj$.Short.parseShort_1_0_Short_ovld(arguments[0]);
            }
        }else if(arguments.length===2){
            if((arguments[0] instanceof String || typeof arguments[0]=="string") && typeof arguments[1]=="number"){
                return this.vj$.Short.parseShort_2_0_Short_ovld(arguments[0],arguments[1]);
            }
        }
    },
    //> private short parseShort_1_0_Short_ovld(String s)
    parseShort_1_0_Short_ovld:function(s){
        return this.parseShort(s,10);
    },
    //> private short parseShort_2_0_Short_ovld(String s,int radix)
    parseShort_2_0_Short_ovld:function(s,radix){
    return  this.vj$.NumberUtil.parseShort(s,radix);
    },
    //> public Short valueOf(String s,int radix)
    //> public Short valueOf(String s)
    //> public Short valueOf(short s)
    valueOf:function(s,radix){
        if(arguments.length===2){
            if((arguments[0] instanceof String || typeof arguments[0]=="string") && typeof arguments[1]=="number"){
                return this.vj$.Short.valueOf_2_0_Short_ovld(arguments[0],arguments[1]);
            }
        }else if(arguments.length===1){
            if(arguments[0] instanceof String || typeof arguments[0]=="string"){
                return this.vj$.Short.valueOf_1_0_Short_ovld(arguments[0]);
            }else if(typeof arguments[0]=="number"){
                return this.vj$.Short.valueOf_1_1_Short_ovld(arguments[0]);
            }
        }
    },
    //> private Short valueOf_2_0_Short_ovld(String s,int radix)
    valueOf_2_0_Short_ovld:function(s,radix){
        return new this(this.parseShort(s,radix));
    },
    //> private Short valueOf_1_0_Short_ovld(String s)
    valueOf_1_0_Short_ovld:function(s){
        return this.valueOf(s,10);
    },
    //> private Short valueOf_1_1_Short_ovld(short s)
    valueOf_1_1_Short_ovld:function(s){
        var offset=128;
        var sAsInt=s;
        if(sAsInt>= -128 && sAsInt<=127){
            return this.ShortCache.cache[sAsInt+offset];
        }
        return new this(s);
    },
    //> public Short decode(String nm)
    decode:function(nm){
        var radix=10;
        var index=0;
        var negative=false;
        var result;
        if(vjo.java.lang.StringUtil.startsWith(nm,"-")){
            negative=true;
            index++;
        }
        if(vjo.java.lang.StringUtil.startsWith(nm,"0x",index) || vjo.java.lang.StringUtil.startsWith(nm,"0X",index)){
            index+=2;
            radix=16;
        }else if(vjo.java.lang.StringUtil.startsWith(nm,"#",index)){
            index++;
            radix=16;
        }else if(vjo.java.lang.StringUtil.startsWith(nm,"0",index) && nm.length>1+index){
            index++;
            radix=8;
        }
        if(vjo.java.lang.StringUtil.startsWith(nm,"-",index)){
            throw new this.vj$.NumberFormatException("Negative sign in wrong position");
        }
        try {
            result=this.valueOf(nm.substring(index),radix);
            result=negative?new this(this.vj$.Util.cast(-result.shortValue(),'short')):result;
        }
        catch(e){
            var constant=negative?vjo.java.lang.StringFactory.build("-"+nm.substring(index)):nm.substring(index);
            result=this.valueOf(constant,radix);
        }
        return result;
    },
    //> public short reverseBytes(short i)
    reverseBytes:function(i){
        return this.vj$.Util.cast((((i&0xFF00)>>8)|(i<<8)),'short');
    }
})
.protos({
    value:0, //< private final short
    //> public constructs()
    //> public constructs(short value)
    //> public constructs(String s)
    constructs:function(){
        if(arguments.length===1){
            if(typeof arguments[0]=="number"){
                this.constructs_1_0_Short_ovld(arguments[0]);
            }else if(arguments[0] instanceof String || typeof arguments[0]=="string"){
                this.constructs_1_1_Short_ovld(arguments[0]);
            }
        }
    },
    //> private constructs_1_0_Short_ovld(short value)
    constructs_1_0_Short_ovld:function(value){
        this.base();
        this.value=value;
    },
    //> private constructs_1_1_Short_ovld(String s)
    constructs_1_1_Short_ovld:function(s){
        this.base();
        this.value=this.vj$.Short.parseShort(s,10);
    },
    //> public byte byteValue()
    byteValue:function(){
        return this.vj$.Util.cast(this.value,'byte');
    },
    //> public short shortValue()
    shortValue:function(){
        return this.value;
    },
    //> public int intValue()
    intValue:function(){
        return this.vj$.Util.cast(this.value,'int');
    },
    //> public long longValue()
    longValue:function(){
        return this.vj$.Util.cast(this.value,'long');
    },
    //> public float floatValue()
    floatValue:function(){
        return this.vj$.Util.cast(this.value,'float');
    },
    //> public double doubleValue()
    doubleValue:function(){
        return this.vj$.Util.cast(this.value,'double');
    },
    //> public String toString()
    toString:function(){
        return vjo.java.lang.StringUtil._valueOf(this.vj$.Util.cast(this.value,'int'));
    },
    //> public int hashCode()
    hashCode:function(){
        return this.vj$.Util.cast(this.value,'int');
    },
    //> public boolean equals(vjo.Object obj)
    equals:function(obj){
        if(obj instanceof this.vj$.Short){
        	var shortObj = obj; //<< Short
            return this.value===shortObj.shortValue();
        }
        return false;
    },
    //> public int compareTo(Short anotherShort)
    compareTo:function(anotherShort){
        return this.value-anotherShort.value;
    }
})
.endType();