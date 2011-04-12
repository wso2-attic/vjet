vjo.ctype('vjo.java.util.Random') //< public
.needs(['vjo.java.lang.System','vjo.java.lang.IllegalArgumentException',
        'vjo.java.lang.Util','vjo.java.lang.Integer','vjo.java.lang.Math'])
.satisfies('vjo.java.io.Serializable')
.props({
    serialVersionUID:3905348978240129619, //< final long
    multiplier:0x5DEECE66D, //< private final long
    addend:0xB, //< private final long
    mask:0, //< private final long
    seedUniquifier:8682522807148012, //< private long
    BITS_PER_BYTE:8, //< private final int
    BYTES_PER_INT:4 //< private final int
})
.protos({
    seed:0, //< private long
    nextNextGaussian:0, //< private double
    haveNextNextGaussian:false, //< private boolean
    //> public constructs()
    //> public constructs(long seed)
    constructs:function(){
        if(arguments.length===0){
            this.constructs_0_0_Random_ovld();
        }else if(arguments.length===1){
            this.constructs_1_0_Random_ovld(arguments[0]);
        }
    },
    //> private constructs_0_0_Random_ovld()
    constructs_0_0_Random_ovld:function(){
    },
    //> private constructs_1_0_Random_ovld(long seed)
    constructs_1_0_Random_ovld:function(seed){
    },
    //> public void setSeed(long seed)
    setSeed:function(seed){
    },
    //> protected int next(int bits)
    next:function(bits){
        return 0;
    },
    //> public void nextBytes(byte[] bytes)
    nextBytes:function(bytes){
        var numRequested=bytes.length; //<int
        var numGot=0,rnd=0; //<int
        for (var i=0;i<numRequested;i++){
            var rnd = this.nextInt(256);
            bytes[i]=this.vj$.Util.cast(rnd,'byte');
        }
    },
    //> public int nextInt()
    //> public int nextInt(int n)
    nextInt:function(){
        if(arguments.length===0){
            return this.nextInt_0_0_Random_ovld();
        }else if(arguments.length===1){
            return this.nextInt_1_0_Random_ovld(arguments[0]);
        }
    },
    //> private int nextInt_0_0_Random_ovld()
    nextInt_0_0_Random_ovld:function(){
        var span = this.vj$.Integer.MAX_VALUE - this.vj$.Integer.MIN_VALUE + 1;
        return this.vj$.Math.floor(this.vj$.Math.random() * span) + this.vj$.Integer.MIN_VALUE;
    },
    //> private int nextInt_1_0_Random_ovld(int n)
    nextInt_1_0_Random_ovld:function(n){
        if(n<=0){
            throw new this.vj$.IllegalArgumentException("n must be positive");
        }
        return this.vj$.Math.floor(this.vj$.Math.random() * n);
    },
    //> public long nextLong()
    nextLong:function(){
        return (this.vj$.Util.cast((this.next(32)),'long')<<32)+this.next(32);
    },
    //> public boolean nextBoolean()
    nextBoolean:function(){
        return this.vj$.Math.random()>=0.5;
    },
    //> public float nextFloat()
    nextFloat:function(){
    	return this.vj$.Math.random();
    },
    //> public double nextDouble()
    nextDouble:function(){
    	return this.vj$.Math.random();
    },
    //> public double nextGaussian()
    nextGaussian:function(){
        if(this.haveNextNextGaussian){
            this.haveNextNextGaussian=false;
            return this.nextNextGaussian;
        }else {
            var v1,v2,s; //<double
            do{
                v1=2*this.nextDouble()-1;
                v2=2*this.nextDouble()-1;
                s=v1*v1+v2*v2;
            }while(s>=1 || s===0);
            var multiplier=this.vj$.Math.sqrt(parseFloat("" +(-2*this.vj$.Math.log(s)/s))); //<double
            this.nextNextGaussian=v2*multiplier;
            this.haveNextNextGaussian=true;
            return v1*multiplier;
        }
    }
})
.inits(function(){
    this.vj$.Random.mask=(1<<48)-1;
})
.endType();