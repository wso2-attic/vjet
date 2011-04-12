vjo.ctype('vjo.java.lang.Float') //< public final
.needs(['vjo.java.lang.StringUtil','vjo.java.lang.Util'])
.needs('vjo.java.lang.MathUtil','')
.inherits('vjo.java.lang.Number')
.satisfies('vjo.java.lang.Comparable<Float>')
.props({
    POSITIVE_INFINITY:0, //< public final float
    NEGATIVE_INFINITY:0, //< public final float
    NaN:0, //< public final float
    MAX_VALUE:3.4028235e+38, //< public final float
    MIN_VALUE:1.4e-45, //< public final float
    SIZE:32, //< public final int
    //> public String toString(float f)
    toString:function(f){
        return vjo.java.lang.StringUtil._valueOf(f);
    },
    //> public String toHexString(float f)
    toHexString:function(f){
        return vjo.java.lang.MathUtil.dec2Hex(f);
    },
    //> public Float valueOf(String s)
    //> public Float valueOf(float f)
    valueOf:function(s){
        if(arguments.length===1){
            if(arguments[0] instanceof String || typeof arguments[0]=="string"){
                return this.vj$.Float.valueOf_1_0_Float_ovld(arguments[0]);
            }else if(typeof arguments[0]=="number"){
                return this.vj$.Float.valueOf_1_1_Float_ovld(arguments[0]);
            }
        }
    },
    //> private Float valueOf_1_0_Float_ovld(String s)
    valueOf_1_0_Float_ovld:function(s){
        return new this(Number(s));
    },
    //> private Float valueOf_1_1_Float_ovld(float f)
    valueOf_1_1_Float_ovld:function(f){
        return new vjo.java.lang.Float(f);
    },
    //> public float parseFloat(String s)
    parseFloat:function(s){
        return Number(s);
    },
    //> public boolean isNaN(float v)
    isNaN:function(v){
        return isNaN(v);
    },
    //> public boolean isInfinite(float v)
    isInfinite:function(v){
        return (v===this.POSITIVE_INFINITY)||(v===this.NEGATIVE_INFINITY);
    },
    //> public int compare(float f1,float f2)
    compare:function(f1,f2){
        if(this.isNaN(f1) && this.isNaN(f2)){
            return 0;
        }
        if(this.isNaN(f1)){
            return 1;
        }
        if(this.isNaN(f2)){
            return -1;
        }
        if(f1===f2){
            return 0;
        }
        if(f1<f2){
            return -1;
        }
        if(f1>f2){
            return 1;
        }
        return -1;
    }
})
.protos({
    value:0, //< private final float
    //> public constructs()
    //> public constructs(float value)
    //> public constructs(String s)
    constructs:function(){
        if(arguments.length===1){
            if(typeof arguments[0]=="number"){
                this.constructs_1_0_Float_ovld(arguments[0]);
            }else if(arguments[0] instanceof String || typeof arguments[0]=="string"){
                this.constructs_1_1_Float_ovld(arguments[0]);
            }
        }
    },
    //> private constructs_1_0_Float_ovld(float value)
    constructs_1_0_Float_ovld:function(value){
        this.base();
        this.value=value;
    },
    //> private constructs_1_1_Float_ovld(String s)
    constructs_1_1_Float_ovld:function(s){
        this.constructs_1_0_Float_ovld(this.vj$.Float.valueOf(s).floatValue());
    },
    //> public boolean isNaN()
    isNaN:function(){
        return this.vj$.Float.isNaN(this.value);
    },
    //> public boolean isInfinite()
    isInfinite:function(){
        return this.vj$.Float.isInfinite(this.value);
    },
    //> public String toString()
    toString:function(){
        return vjo.java.lang.StringUtil._valueOf(this.value);
    },
    //> public byte byteValue()
    byteValue:function(){
        return this.vj$.Util.cast(this.value,'byte');
    },
    //> public short shortValue()
    shortValue:function(){
        return this.vj$.Util.cast(this.value,'short');
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
        return this.value;
    },
    //> public double doubleValue()
    doubleValue:function(){
        return this.vj$.Util.cast(this.value,'double');
    },
    //> public int hashCode()
    hashCode:function(){
        return this.vj$.Util.cast(this.value,'int');
    },
    //> public boolean equals(vjo.Object obj)
    equals:function(obj){
        return (vjo.java.lang.Float.clazz.isInstance(obj))&&(this.vj$.Util.cast((obj.value),'long'))===this.vj$.Util.cast(this.value,'long');
    },
    //> public int compareTo(Float anotherFloat)
    compareTo:function(anotherFloat){
        return this.vj$.Float.compare(this.value,anotherFloat.value);
    }
})
.inits(function(){
    this.vj$.Float.POSITIVE_INFINITY=parseFloat(1.0/0.0 + "");
    this.vj$.Float.NEGATIVE_INFINITY=parseFloat(-1.0/0.0 + "");
    this.vj$.Float.NaN=parseFloat(0.0/0.0 + "");
})
.endType();