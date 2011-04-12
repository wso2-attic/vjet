vjo.ctype('vjo.java.lang.Double') //< public final
.needs(['vjo.java.lang.StringUtil','vjo.java.lang.Util'])
.needs('vjo.java.lang.MathUtil','')
.inherits('vjo.java.lang.Number')
.satisfies('vjo.java.lang.Comparable<Double>')
.props({
    POSITIVE_INFINITY:0, //< public final double
    NEGATIVE_INFINITY:0, //< public final double
    NaN:0, //< public final double
    MAX_VALUE:1.7976931348623157e+308, //< public final double
    MIN_VALUE:4.9e-324, //< public final double
    SIZE:64, //< public final int
    //> public String toString(double d)
    toString:function(d){
        return vjo.java.lang.StringUtil._valueOf(d);
    },
    //> public String toHexString(double d)
    toHexString:function(d){
        return vjo.java.lang.MathUtil.dec2Hex(d);
    },
    //> public Double valueOf(String s)
    //> public Double valueOf(double d)
    valueOf:function(s){
        if(arguments.length===1){
            if(arguments[0] instanceof String || typeof arguments[0]=="string"){
                return this.vj$.Double.valueOf_1_0_Double_ovld(arguments[0]);
            }else if(typeof arguments[0]=="number"){
                return this.vj$.Double.valueOf_1_1_Double_ovld(arguments[0]);
            }
        }
    },
    //> private Double valueOf_1_0_Double_ovld(String s)
    valueOf_1_0_Double_ovld:function(s){
        return new this(parseFloat(s));
    },
    //> private Double valueOf_1_1_Double_ovld(double d)
    valueOf_1_1_Double_ovld:function(d){
        return new this(d);
    },
    //> public double parseDouble(String s)
    parseDouble:function(s){
        return parseFloat(s);
    },
    //> public boolean isNaN(double v)
    isNaN:function(v){
        return isNaN(v);
    },
    //> public boolean isInfinite(double v)
    isInfinite:function(v){
        return (v===this.POSITIVE_INFINITY)||(v===this.NEGATIVE_INFINITY);
    },
    //> public int compare(double d1,double d2)
    compare:function(d1,d2){
        if(this.isNaN(d1) && this.isNaN(d2)){
            return 0;
        }
        if(this.isNaN(d1)){
            return 1;
        }
        if(this.isNaN(d2)){
            return -1;
        }
        if(d1===d2){
            return 0;
        }
        if(d1<d2){
            return -1;
        }
        if(d1>d2){
            return 1;
        }
        return -1;
    },
    //> private boolean isFinite(double d)
    isFinite:function(d){
        return isFinite(d);
    }
})
.protos({
    value:0, //< private final double
    //> public constructs()
    //> public constructs(double value)
    //> public constructs(String s)
    constructs:function(){
        if(arguments.length===1){
            if(typeof arguments[0]=="number"){
                this.constructs_1_0_Double_ovld(arguments[0]);
            }else if(arguments[0] instanceof String || typeof arguments[0]=="string"){
                this.constructs_1_1_Double_ovld(arguments[0]);
            }
        }
    },
    //> private constructs_1_0_Double_ovld(double value)
    constructs_1_0_Double_ovld:function(value){
        this.base();
        this.value=value;
    },
    //> private constructs_1_1_Double_ovld(String s)
    constructs_1_1_Double_ovld:function(s){
        this.constructs_1_0_Double_ovld(this.vj$.Double.valueOf(s).doubleValue());
    },
    //> public boolean isNaN()
    isNaN:function(){
        return this.vj$.Double.isNaN(this.value);
    },
    //> public boolean isInfinite()
    isInfinite:function(){
        return this.vj$.Double.isInfinite(this.value);
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
        return this.vj$.Util.cast(this.value,'float');
    },
    //> public double doubleValue()
    doubleValue:function(){
        return this.value;
    },
    //> public int hashCode()
    hashCode:function(){
        var bits=this.vj$.Util.cast(this.value,'long');
        return this.vj$.Util.cast(bits,'int');
    },
    //> public boolean equals(vjo.Object obj)
    equals:function(obj){
        return (vjo.java.lang.Double.clazz.isInstance(obj))&&(this.vj$.Util.cast((obj.value),'long'))===this.vj$.Util.cast(this.value,'long');
    },
    //> public int compareTo(Double anotherDouble)
    compareTo:function(anotherDouble){
        return this.vj$.Double.compare(this.value,anotherDouble.value);
    }
})
.inits(function(){
    this.vj$.Double.POSITIVE_INFINITY=parseFloat(1.0/0.0 + "");
    this.vj$.Double.NEGATIVE_INFINITY=parseFloat(-1.0/0.0 + "");
    this.vj$.Double.NaN=parseFloat(0.0/0.0 + "");
})
.endType();