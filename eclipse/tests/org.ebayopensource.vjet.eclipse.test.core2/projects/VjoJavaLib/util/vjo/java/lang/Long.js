vjo.ctype('vjo.java.lang.Long') //< public final
.needs(['vjo.java.lang.NumberFormatException','vjo.java.lang.Character',
    'vjo.java.lang.Integer','vjo.java.lang.Util',
    'vjo.java.lang.StringUtil','vjo.java.lang.NumberUtil'])
.needs('vjo.java.lang.StringFactory','')
.inherits('vjo.java.lang.Number')
.satisfies('vjo.java.lang.Comparable<Long>')
.props({
    MIN_VALUE:-0x8000000000000000, //< public final long
    MAX_VALUE:0x7fffffffffffffff, //< public final long
    SIZE:64, //< public final int
    LongCache:vjo.ctype() //< private
    .props({
        cache:null //< final Long[]
    })
    .protos({
        //> private constructs()
        constructs:function(){
        }
    })
    .inits(function(){
        this.vj$.LongCache.cache=vjo.createArray(null,  -(-128)+127+1);
        {
            for (var i=0;i<this.vj$.LongCache.cache.length;i++){
                this.cache[i]=new this.vj$.Long(i-128);
            }
        }
    })
    .endType(),
    //> public String toHexString(long i)
    toHexString:function(i){
        return this.toUnsignedString(i,4);
    },
    //> public String toOctalString(long i)
    toOctalString:function(i){
        return this.toUnsignedString(i,3);
    },
    //> public String toBinaryString(long i)
    toBinaryString:function(i){
        return this.toUnsignedString(i,1);
    },
    //> private String toUnsignedString(long i,int shift)
    toUnsignedString:function(i,shift){
        var buf=vjo.createArray("", 64);
        var charPos=64;
        var radix=1<<shift;
        var mask=radix-1;
        do{
            buf[--charPos]=this.vj$.Integer.digits[this.vj$.Util.cast((i&mask),'int')];
            i>>>=shift;
        }while(i!==0);
        return vjo.java.lang.StringFactory.build(buf,charPos,(64-charPos));
    },
    //> public String toString(long i,int radix)
    //> public String toString(long i)
    toString:function(i,radix){
        if(arguments.length===2){
            if(typeof arguments[0]=="number" && typeof arguments[1]=="number"){
                return this.vj$.Long.toString_2_0_Long_ovld(arguments[0],arguments[1]);
            }
        }else if(arguments.length===1){
            if(typeof arguments[0]=="number"){
                return this.vj$.Long.toString_1_0_Long_ovld(arguments[0]);
            }
        }
    },
    //> private String toString_2_0_Long_ovld(long i,int radix)
    toString_2_0_Long_ovld:function(i,radix){
        if(radix<this.vj$.Character.MIN_RADIX || radix>this.vj$.Character.MAX_RADIX){
            radix=10;
        }
        if(radix===10){
            return this.toString(i);
        }
        var buf=vjo.createArray("", 65);
        var charPos=64;
        var negative=(i<0);
        if(!negative){
            i=-i;
        }
        while(i<=-radix){
            buf[charPos--]=this.vj$.Integer.digits[this.vj$.Util.cast((-(i%radix)),'int')];
            i=parseInt(i/radix);
        }
        buf[charPos]=this.vj$.Integer.digits[this.vj$.Util.cast((-i),'int')];
        if(negative){
            buf[--charPos]='-';
        }
        return vjo.java.lang.StringFactory.build(buf,charPos,(65-charPos));
    },
    //> private String toString_1_0_Long_ovld(long i)
    toString_1_0_Long_ovld:function(i){
        if(i===this.MIN_VALUE){
            return "-9223372036854775808";
        }
        var size=(i<0)?this.stringSize(-i)+1:this.stringSize(i);
        var buf=vjo.createArray("", size);
        this.getChars(i,size,buf);
        return vjo.java.lang.StringFactory.build(buf,0,size);
    },
    //> void getChars(long i,int index,char[] buf)
    getChars:function(i,index,buf){
        var q;
        var r;
        var charPos=index;
        var sign=0;
        if(i<0){
            sign='-';
            i=-i;
        }
        while(i>this.vj$.Integer.MAX_VALUE){
            q=parseInt(i/100);
            r=this.vj$.Util.cast((i-((q<<6)+(q<<5)+(q<<2))),'int');
            i=q;
            buf[--charPos]=this.vj$.Integer.DigitOnes[r];
            buf[--charPos]=this.vj$.Integer.DigitTens[r];
        }
        var q2;
        var i2=this.vj$.Util.cast(i,'int');
        while(i2>=65536){
            q2=parseInt(i2/100);
            r=i2-((q2<<6)+(q2<<5)+(q2<<2));
            i2=q2;
            buf[--charPos]=this.vj$.Integer.DigitOnes[r];
            buf[--charPos]=this.vj$.Integer.DigitTens[r];
        }
        for (;;){
            q2=(i2*52429)>>>(16+3);
            r=i2-((q2<<3)+(q2<<1));
            buf[--charPos]=this.vj$.Integer.digits[r];
            i2=q2;
            if(i2===0){
                break;
            }
        }
        if(sign!==0){
            buf[--charPos]=sign;
        }
    },
    //> int stringSize(long x)
    stringSize:function(x){
        var p=10;
        for (var i=1;i<19;i++){
            if(x<p){
                return i;
            }
            p=10*p;
        }
        return 19;
    },
    //> public long parseLong(String s,int radix)
    //> public long parseLong(String s)
    parseLong:function(s,radix){
        if(arguments.length===2){
            if((arguments[0] instanceof String || typeof arguments[0]=="string") && typeof arguments[1]=="number"){
                return this.vj$.Long.parseLong_2_0_Long_ovld(arguments[0],arguments[1]);
            }
        }else if(arguments.length===1){
            if(arguments[0] instanceof String || typeof arguments[0]=="string"){
                return this.vj$.Long.parseLong_1_0_Long_ovld(arguments[0]);
            }
        }
    },
    //> private long parseLong_2_0_Long_ovld(String s,int radix)
    parseLong_2_0_Long_ovld:function(s,radix){
    return  this.vj$.NumberUtil.parseLong(s,radix);
    },
    //> private long parseLong_1_0_Long_ovld(String s)
    parseLong_1_0_Long_ovld:function(s){
        return this.parseLong(s,10);
    },
    //> public Long valueOf(String s,int radix)
    //> public Long valueOf(String s)
    //> public Long valueOf(long l)
    valueOf:function(s,radix){
        if(arguments.length===2){
            if((arguments[0] instanceof String || typeof arguments[0]=="string") && typeof arguments[1]=="number"){
                return this.vj$.Long.valueOf_2_0_Long_ovld(arguments[0],arguments[1]);
            }
        }else if(arguments.length===1){
            if(arguments[0] instanceof String || typeof arguments[0]=="string"){
                return this.vj$.Long.valueOf_1_0_Long_ovld(arguments[0]);
            }else if(typeof arguments[0]=="number"){
                return this.vj$.Long.valueOf_1_1_Long_ovld(arguments[0]);
            }
        }
    },
    //> private Long valueOf_2_0_Long_ovld(String s,int radix)
    valueOf_2_0_Long_ovld:function(s,radix){
        return new this(this.parseLong(s,radix));
    },
    //> private Long valueOf_1_0_Long_ovld(String s)
    valueOf_1_0_Long_ovld:function(s){
        return new this(this.parseLong(s,10));
    },
    //> private Long valueOf_1_1_Long_ovld(long l)
    valueOf_1_1_Long_ovld:function(l){
        var offset=128;
        if(l>= -128 && l<=127){
            return this.LongCache.cache[this.vj$.Util.cast(l,'int')+offset];
        }
        return new this(l);
    },
    //> public Long decode(String nm)
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
            result=negative?new this(this.vj$.Util.cast(-result.longValue(),'long')):result;
        }
        catch(e){
            var constant=negative?vjo.java.lang.StringFactory.build("-"+nm.substring(index)):nm.substring(index);
            result=this.valueOf(constant,radix);
        }
        return result;
    },
    //> public long highestOneBit(long i)
    highestOneBit:function(i){
        i|=(i>>1);
        i|=(i>>2);
        i|=(i>>4);
        i|=(i>>8);
        i|=(i>>16);
        i|=(i>>32);
        return i-(i>>>1);
    },
    //> public long lowestOneBit(long i)
    lowestOneBit:function(i){
        return i& -i;
    },
    //> public int numberOfLeadingZeros(long i)
    numberOfLeadingZeros:function(i){
        if(i===0){
            return 64;
        }
        var n=1;
        var x=this.vj$.Util.cast((i>>>32),'int');
        if(x===0){
            n+=32;
            x=this.vj$.Util.cast(i,'int');
        }
        if(x>>>16===0){
            n+=16;
            x<<=16;
        }
        if(x>>>24===0){
            n+=8;
            x<<=8;
        }
        if(x>>>28===0){
            n+=4;
            x<<=4;
        }
        if(x>>>30===0){
            n+=2;
            x<<=2;
        }
        n-=x>>>31;
        return n;
    },
    //> public int numberOfTrailingZeros(long i)
    numberOfTrailingZeros:function(i){
        var x,y;
        if(i===0){
            return 64;
        }
        var n=63;
        y=this.vj$.Util.cast(i,'int');
        if(y!==0){
            n=n-32;
            x=y;
        }else {
            x=this.vj$.Util.cast((i>>>32),'int');
        }
        y=x<<16;
        if(y!==0){
            n=n-16;
            x=y;
        }
        y=x<<8;
        if(y!==0){
            n=n-8;
            x=y;
        }
        y=x<<4;
        if(y!==0){
            n=n-4;
            x=y;
        }
        y=x<<2;
        if(y!==0){
            n=n-2;
            x=y;
        }
        return n-((x<<1)>>>31);
    },
    //> public int bitCount(long i)
    bitCount:function(i){
        i=i-((i>>>1)&0x5555555555555555);
        i=(i&0x3333333333333333)+((i>>>2)&0x3333333333333333);
        i=(i+(i>>>4))&0x0f0f0f0f0f0f0f0f;
        i=i+(i>>>8);
        i=i+(i>>>16);
        i=i+(i>>>32);
        return this.vj$.Util.cast(i,'int')&0x7f;
    },
    //> public long rotateLeft(long i,int distance)
    rotateLeft:function(i,distance){
        return (i<<distance)|(i>>>-distance);
    },
    //> public long rotateRight(long i,int distance)
    rotateRight:function(i,distance){
        return (i>>>distance)|(i<<-distance);
    },
    //> public long reverse(long i)
    reverse:function(i){
        i=(i&0x5555555555555555)<<1|(i>>>1)&0x5555555555555555;
        i=(i&0x3333333333333333)<<2|(i>>>2)&0x3333333333333333;
        i=(i&0x0f0f0f0f0f0f0f0f)<<4|(i>>>4)&0x0f0f0f0f0f0f0f0f;
        i=(i&0x00ff00ff00ff00ff)<<8|(i>>>8)&0x00ff00ff00ff00ff;
        i=(i<<48)|((i&0xffff0000)<<16)|((i>>>16)&0xffff0000)|(i>>>48);
        return i;
    },
    //> public int signum(long i)
    signum:function(i){
        return this.vj$.Util.cast(((i>>63)|(-i>>>63)),'int');
    },
    //> public long reverseBytes(long i)
    reverseBytes:function(i){
        i=(i&0x00ff00ff00ff00ff)<<8|(i>>>8)&0x00ff00ff00ff00ff;
        return (i<<48)|((i&0xffff0000)<<16)|((i>>>16)&0xffff0000)|(i>>>48);
    }
})
.protos({
    value:0, //< private final long
    //> public constructs()
    //> public constructs(long value)
    //> public constructs(String s)
    constructs:function(){
        if(arguments.length===1){
            if(typeof arguments[0]=="number"){
                this.constructs_1_0_Long_ovld(arguments[0]);
            }else if(arguments[0] instanceof String || typeof arguments[0]=="string"){
                this.constructs_1_1_Long_ovld(arguments[0]);
            }
        }
    },
    //> private constructs_1_0_Long_ovld(long value)
    constructs_1_0_Long_ovld:function(value){
        this.base();
        this.value=value;
    },
    //> private constructs_1_1_Long_ovld(String s)
    constructs_1_1_Long_ovld:function(s){
        this.base();
        this.value=this.vj$.Long.parseLong(s,10);
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
        return this.value;
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
        return this.vj$.Util.cast((this.value^(this.value>>>32)),'int');
    },
    //> public boolean equals(vjo.Object obj)
    equals:function(obj){
        if(obj instanceof this.vj$.Long){
            return this.value===obj.longValue();
        }
        return false;
    },
    //> public int compareTo(Long anotherLong)
    compareTo:function(anotherLong){
        var thisVal=this.value;
        var anotherVal=anotherLong.value;
        return (thisVal<anotherVal?-1:(thisVal===anotherVal?0:1));
    }
})
.endType();