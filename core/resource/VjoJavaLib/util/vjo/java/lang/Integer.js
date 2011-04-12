vjo.ctype('vjo.java.lang.Integer') //< public final
.needs(['vjo.java.lang.NumberFormatException','vjo.java.lang.Character',
    'vjo.java.lang.Util','vjo.java.lang.StringUtil','vjo.java.lang.NumberUtil'])
.needs('vjo.java.lang.StringFactory','')
.inherits('vjo.java.lang.Number')
.satisfies('vjo.java.lang.Comparable<Integer>')
.props({
    MIN_VALUE:-2147483648, //< public final int
    MAX_VALUE:0x7fffffff, //< public final int
    digits:null, //< final char[]
    DigitTens:null, //< final char[]
    DigitOnes:null, //< final char[]
    sizeTable:null, //< final int[]
    SIZE:32, //< public final int
    IntegerCache:vjo.ctype() //< private
    .props({
        cache:null //< final Integer[]
    })
    .protos({
        //> private constructs()
        constructs:function(){
        }
    })
    .inits(function(){
        this.vj$.IntegerCache.cache=vjo.createArray(null,  -(-128)+127+1);
        {
            for (var i=0;i<this.vj$.IntegerCache.cache.length;i++){
                this.cache[i]=new this.vj$.Integer(i-128);
            }
        }
    })
    .endType(),
    //> public String toHexString(int i)
    toHexString:function(i){
        return this.toUnsignedString(i,4);
    },
    //> public String toOctalString(int i)
    toOctalString:function(i){
        return this.toUnsignedString(i,3);
    },
    //> public String toBinaryString(int i)
    toBinaryString:function(i){
        return this.toUnsignedString(i,1);
    },
    //> private String toUnsignedString(int i,int shift)
    toUnsignedString:function(i,shift){
        var buf=vjo.createArray("", 32);
        var charPos=32;
        var radix=1<<shift;
        var mask=radix-1;
        do{
            buf[--charPos]=this.digits[i&mask];
            i>>>=shift;
        }while(i!==0);
        return vjo.java.lang.StringFactory.build(buf,charPos,(32-charPos));
    },
    //> public String toString(int i,int radix)
    //> public String toString(int i)
    toString:function(i,radix){
        if(arguments.length===2){
            if(typeof arguments[0]=="number" && typeof arguments[1]=="number"){
                return this.vj$.Integer.toString_2_0_Integer_ovld(arguments[0],arguments[1]);
            }
        }else if(arguments.length===1){
            if(typeof arguments[0]=="number"){
                return this.vj$.Integer.toString_1_0_Integer_ovld(arguments[0]);
            }
        }
    },
    //> private String toString_2_0_Integer_ovld(int i,int radix)
    toString_2_0_Integer_ovld:function(i,radix){
        if(radix<this.vj$.Character.MIN_RADIX || radix>this.vj$.Character.MAX_RADIX){
            radix=10;
        }
        if(radix===10){
            return this.toString(i);
        }
        var buf=vjo.createArray("", 33);
        var negative=(i<0);
        var charPos=32;
        if(!negative){
            i=-i;
        }
        while(i<=-radix){
            buf[charPos--]=this.digits[-(i%radix)];
            i=parseInt(i/radix + "");
        }
        buf[charPos]=this.digits[-i];
        if(negative){
            buf[--charPos]='-';
        }
        return vjo.java.lang.StringFactory.build(buf,charPos,(33-charPos));
    },
    //> private String toString_1_0_Integer_ovld(int i)
    toString_1_0_Integer_ovld:function(i){
        if(i===this.MIN_VALUE){
            return "-2147483648";
        }
        var size=(i<0)?this.stringSize(-i)+1:this.stringSize(i);
        var buf=vjo.createArray("", size);
        this.getChars(i,size,buf);
        return vjo.java.lang.StringFactory.build(buf,0,size);
    },
    //> void getChars(int i,int index,char[] buf)
    getChars:function(i,index,buf){
        var q,r;
        var charPos=index;
        var sign=0;
        if(i<0){
            sign='-';
            i=-i;
        }
        while(i>=65536){
            q=parseInt(i/100 + "");
            r=i-(q*100);
            i=q;
            buf[--charPos]=this.DigitOnes[r];
            buf[--charPos]=this.DigitTens[r];
        }
        while(i>=100){
            q=(i*52429)>>>(16+3);
            r=i-((q<<3)+(q<<1));
            buf[--charPos]=this.digits[r];
            i=q;
        }
        if(i>=10){
            buf[--charPos]=this.DigitOnes[i];
            buf[--charPos]=this.DigitTens[i];
        }else {
            buf[--charPos]=this.DigitOnes[i];
        }
        if(sign!==0){
            buf[--charPos]=sign;
        }
    },
    //> int stringSize(int x)
    stringSize:function(x){
        for (var i=0;;i++){
            if(x<=this.sizeTable[i]){
                return i+1;
            }
        }
    },
    //> public int parseInt(String s,int radix)
    //> public int parseInt(String s)
    parseInt:function(s,radix){
        if(arguments.length===2){
            if((arguments[0] instanceof String || typeof arguments[0]=="string") && typeof arguments[1]=="number"){
                return this.vj$.Integer.parseInt_2_0_Integer_ovld(arguments[0],arguments[1]);
            }
        }else if(arguments.length===1){
            if(arguments[0] instanceof String || typeof arguments[0]=="string"){
                return this.vj$.Integer.parseInt_1_0_Integer_ovld(arguments[0]);
            }
        }
    },
    //> private int parseInt_2_0_Integer_ovld(String s,int radix)
    parseInt_2_0_Integer_ovld:function(s,radix){
    return  this.vj$.NumberUtil.parseInteger(s,radix);
    },
    //> private int parseInt_1_0_Integer_ovld(String s)
    parseInt_1_0_Integer_ovld:function(s){
        return this.parseInt(s,10);
    },
    //> public Integer valueOf(String s,int radix)
    //> public Integer valueOf(String s)
    //> public Integer valueOf(int i)
    valueOf:function(s,radix){
        if(arguments.length===2){
            if((arguments[0] instanceof String || typeof arguments[0]=="string") && typeof arguments[1]=="number"){
                return this.vj$.Integer.valueOf_2_0_Integer_ovld(arguments[0],arguments[1]);
            }
        }else if(arguments.length===1){
            if(arguments[0] instanceof String || typeof arguments[0]=="string"){
                return this.vj$.Integer.valueOf_1_0_Integer_ovld(arguments[0]);
            }else if(typeof arguments[0]=="number"){
                return this.vj$.Integer.valueOf_1_1_Integer_ovld(arguments[0]);
            }
        }
    },
    //> private Integer valueOf_2_0_Integer_ovld(String s,int radix)
    valueOf_2_0_Integer_ovld:function(s,radix){
        return new this(this.parseInt(s,radix));
    },
    //> private Integer valueOf_1_0_Integer_ovld(String s)
    valueOf_1_0_Integer_ovld:function(s){
        return new this(this.parseInt(s,10));
    },
    //> private Integer valueOf_1_1_Integer_ovld(int i)
    valueOf_1_1_Integer_ovld:function(i){
        var offset=128;
        if(i>= -128 && i<=127){
            var ints=this.IntegerCache.cache;
            return ints[i+offset];
        }
        return new this(i);
    },
    //> public Integer decode(String nm)
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
            result=negative?new this(-result.intValue()):result;
        }
        catch(e){
            var constant=negative?vjo.java.lang.StringFactory.build("-"+nm.substring(index)):nm.substring(index);
            result=this.valueOf(constant,radix);
        }
        return result;
    },
    //> public int highestOneBit(int i)
    highestOneBit:function(i){
        i|=(i>>1);
        i|=(i>>2);
        i|=(i>>4);
        i|=(i>>8);
        i|=(i>>16);
        return i-(i>>>1);
    },
    //> public int lowestOneBit(int i)
    lowestOneBit:function(i){
        return i& -i;
    },
    //> public int numberOfLeadingZeros(int i)
    numberOfLeadingZeros:function(i){
        if(i===0){
            return 32;
        }
        var n=1;
        if(i>>>16===0){
            n+=16;
            i<<=16;
        }
        if(i>>>24===0){
            n+=8;
            i<<=8;
        }
        if(i>>>28===0){
            n+=4;
            i<<=4;
        }
        if(i>>>30===0){
            n+=2;
            i<<=2;
        }
        n-=i>>>31;
        return n;
    },
    //> public int numberOfTrailingZeros(int i)
    numberOfTrailingZeros:function(i){
        var y;
        if(i===0){
            return 32;
        }
        var n=31;
        y=i<<16;
        if(y!==0){
            n=n-16;
            i=y;
        }
        y=i<<8;
        if(y!==0){
            n=n-8;
            i=y;
        }
        y=i<<4;
        if(y!==0){
            n=n-4;
            i=y;
        }
        y=i<<2;
        if(y!==0){
            n=n-2;
            i=y;
        }
        return n-((i<<1)>>>31);
    },
    //> public int bitCount(int i)
    bitCount:function(i){
        i=i-((i>>>1)&0x55555555);
        i=(i&0x33333333)+((i>>>2)&0x33333333);
        i=(i+(i>>>4))&0x0f0f0f0f;
        i=i+(i>>>8);
        i=i+(i>>>16);
        return i&0x3f;
    },
    //> public int rotateLeft(int i,int distance)
    rotateLeft:function(i,distance){
        return (i<<distance)|(i>>>-distance);
    },
    //> public int rotateRight(int i,int distance)
    rotateRight:function(i,distance){
        return (i>>>distance)|(i<<-distance);
    },
    //> public int reverse(int i)
    reverse:function(i){
        i=(i&0x55555555)<<1|(i>>>1)&0x55555555;
        i=(i&0x33333333)<<2|(i>>>2)&0x33333333;
        i=(i&0x0f0f0f0f)<<4|(i>>>4)&0x0f0f0f0f;
        i=(i<<24)|((i&0xff00)<<8)|((i>>>8)&0xff00)|(i>>>24);
        return i;
    },
    //> public int signum(int i)
    signum:function(i){
        return (i>>31)|(-i>>>31);
    },
    //> public int reverseBytes(int i)
    reverseBytes:function(i){
        return ((i>>>24))|((i>>8)&0xFF00)|((i<<8)&0xFF0000)|((i<<24));
    }
})
.protos({
    value:0, //< private final int
    //> public constructs()
    //> public constructs(int value)
    //> public constructs(String s)
    constructs:function(){
        if(arguments.length===1){
            if(typeof arguments[0]=="number"){
                this.constructs_1_0_Integer_ovld(arguments[0]);
            }else if(arguments[0] instanceof String || typeof arguments[0]=="string"){
                this.constructs_1_1_Integer_ovld(arguments[0]);
            }
        }
    },
    //> private constructs_1_0_Integer_ovld(int value)
    constructs_1_0_Integer_ovld:function(value){
        this.base();
        this.value=value;
    },
    //> private constructs_1_1_Integer_ovld(String s)
    constructs_1_1_Integer_ovld:function(s){
        this.base();
        this.value=this.vj$.Integer.parseInt(s,10);
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
        return this.value;
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
        return vjo.java.lang.StringUtil._valueOf(this.value);
    },
    //> public int hashCode()
    hashCode:function(){
        return this.value;
    },
    //> public boolean equals(Object obj)
    equals:function(obj){
        if(obj instanceof this.vj$.Integer){
            return this.value===obj.intValue();
        }
        return false;
    },
    //> public int compareTo(Integer anotherInteger)
    compareTo:function(anotherInteger){
        var thisVal=this.value;
        var anotherVal=anotherInteger.value;
        return (thisVal<anotherVal?-1:(thisVal===anotherVal?0:1));
    }
})
.inits(function(){
    this.vj$.Integer.digits=['0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'];
    this.vj$.Integer.DigitTens=['0','0','0','0','0','0','0','0','0','0','1','1','1','1','1','1','1','1','1','1','2','2','2','2','2','2','2','2','2','2','3','3','3','3','3','3','3','3','3','3','4','4','4','4','4','4','4','4','4','4','5','5','5','5','5','5','5','5','5','5','6','6','6','6','6','6','6','6','6','6','7','7','7','7','7','7','7','7','7','7','8','8','8','8','8','8','8','8','8','8','9','9','9','9','9','9','9','9','9','9'];
    this.vj$.Integer.DigitOnes=['0','1','2','3','4','5','6','7','8','9','0','1','2','3','4','5','6','7','8','9','0','1','2','3','4','5','6','7','8','9','0','1','2','3','4','5','6','7','8','9','0','1','2','3','4','5','6','7','8','9','0','1','2','3','4','5','6','7','8','9','0','1','2','3','4','5','6','7','8','9','0','1','2','3','4','5','6','7','8','9','0','1','2','3','4','5','6','7','8','9','0','1','2','3','4','5','6','7','8','9'];
    this.vj$.Integer.sizeTable=[9,99,999,9999,99999,999999,9999999,99999999,999999999,this.MAX_VALUE];
})
.endType();