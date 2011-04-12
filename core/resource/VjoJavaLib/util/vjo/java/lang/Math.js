vjo.ctype('vjo.java.lang.Math') //< public final
.needs('vjo.java.lang.Double')
.needs('vjo.java.lang.MathUtil','')
.props({
    E:0, //< public final double
    PI:0, //< public final double
    //> public double abs(double d)
    abs:function(d){
        return Math.abs(d);
    },
    //> public double max(double a,double b)
    max:function(a,b){
        return Math.max(a,b);
    },
    //> public double min(double a,double b)
    min:function(a,b){
        return Math.min(a,b);
    },
    //> public double pow(double d,double d1)
    pow:function(d,d1){
        return Math.pow(d,d1);
    },
    //> public long round(double d)
    round:function(d){
        return Math.round(d);
    },
    //> public double sqrt(double d)
    sqrt:function(d){
        return Math.sqrt(d);
    },
    //> public double log10(double a)
    log10:function(a){
        return vjo.java.lang.MathUtil.log10(a);
    },
    //> public double cbrt(double a)
    cbrt:function(a){
        return vjo.java.lang.MathUtil.cbrt(a);
    },
    //> public double signum(double d)
    signum:function(d){
        return vjo.java.lang.MathUtil.signum(d);
    },
    //> public double sinh(double x)
    sinh:function(x){
        return vjo.java.lang.MathUtil.sinh(x);
    },
    //> public double cosh(double x)
    cosh:function(x){
        return vjo.java.lang.MathUtil.cosh(x);
    },
    //> public double tanh(double x)
    tanh:function(x){
        return vjo.java.lang.MathUtil.tanh(x);
    },
    //> public double hypot(double x,double y)
    hypot:function(x,y){
        return vjo.java.lang.MathUtil.hypot(x,y);
    },
    //> public double expm1(double x)
    expm1:function(x){
        return vjo.java.lang.MathUtil.expm1(x);
    },
    //> public double log1p(double x)
    log1p:function(x){
        return vjo.java.lang.MathUtil.log1p(x);
    },
    //> public double random()
    random:function(){
        return Math.random();
    },
    //> public double toRadians(double angdeg)
    toRadians:function(angdeg){
        return (angdeg/180)*this.PI;
    },
    //> public double toDegrees(double angrad)
    toDegrees:function(angrad){
        return (angrad*180)/this.PI;
    },
    //> public double sin(double x)
    sin:function(x){
        return Math.sin(x);
    },
    //> public double cos(double x)
    cos:function(x){
        return Math.cos(x);
    },
    //> public double tan(double x)
    tan:function(x){
        return Math.tan(x);
    },
    //> public double floor(double x)
    floor:function(x){
        return Math.floor(x);
    },
    //> public double ceil(double x)
    ceil:function(x){
        return Math.ceil(x);
    },
    //> public double exp(double x)
    exp:function(x){
        return Math.exp(x);
    },
    //> public double log(double x)
    log:function(x){
        return Math.log(x);
    },
    //> public double atan(double x)
    atan:function(x){
        return Math.atan(x);
    },
    //> public double asin(double x)
    asin:function(x){
        return Math.asin(x);
    },
    //> public double acos(double x)
    acos:function(x){
        return Math.acos(x);
    },
    //> public double atan2(double y,double x)
    atan2:function(y,x){
        return Math.atan2(y,x);
    },
    //> public double rint(double a)
    rint:function(a){
        return vjo.java.lang.MathUtil.rint(a);
    },
    //> public double IEEEremainder(double x,double p)
    IEEEremainder:function(x,p){
        var r=this.abs(x%p);
        if(this.vj$.Double.isNaN(r)||r===p || r<=parseFloat(this.abs(p)/2.0)){
            return r;
        }else {
            return this.signum(x)*(r-p);
        }
    }
})
.protos({
    //> private constructs()
    constructs:function(){
    }
})
.inits(function(){
    this.vj$.Math.E=Math.E;
    this.vj$.Math.PI=Math.PI;
})
.endType();