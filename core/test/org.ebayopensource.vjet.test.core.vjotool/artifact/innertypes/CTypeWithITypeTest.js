vjo.ctype('<<0>>innertypes.<<1>>CTypeWithITypeTest') //< public
.props({
    staticOuterProp:"Test", //< String

    InnerStaticInterface:<<2>>vjo.<<3>>itype() //< public
    .<<4>>p<<5>>rops({
        innerISProp:null, //< public final String
        innerISProp1:null //< public String
    })
    .protos({
        //> public void innerIFunc()
        innerISFunc:<<6>>vjo.<<7>>NEEDS_IMPL
    })
    .<<8>>inits(function(){
    	<<9>>this.<<10>>vj$.<<11>>CTypeWithIType.InnerStaticInterface.<<12>>innerISProp=<<13>>this.<<14>>vj$.<<15>>CTypeWithIType.<<16>>outerStaticFunc().<<17>>big();
        this.vj$.CTypeWithIType.InnerStaticInterface.<<18>>innerISProp1=this.vj$.CTypeWithIType.<<19>>staticOuterProp.<<20>>big();
    })
    .<<21>>endType(),
    //> public String outerStaticFunc()
    outerStaticFunc:function(){
    	<<22>>vjo.<<23>>sysout.<<24>>println(<<25>>this.<<26>>staticOuterProp);
    	<<27>>return <<28>>"";
    }
})
.<<29>>protos({
    outerProp:"test", //< String
    //> public String outerFunc1()
    outerFunc1:function(){
    	<<30>>vjo.<<31>>sysout.<<32>>println(<<33>>this.<<34>>outerProp);
        <<35>>return <<36>>"";
    },
    //> public void outerFunc2()
    outerFunc2:function(){
    	<<37>>var innerCType=<<38>>new <<39>>this.<<40>>InnerCType(); //<InnerCType
    	<<41>>innerCType.<<42>>innerISFunc();
    },
    
    InnerInterface:<<43>>vjo.<<44>>itype() //< public
    .<<45>>props({
        innerIProp:null, //< public final String
        innerIProp1:null //< public String
    })
    .<<46>>protos({
        //> public void innerIFunc()
        innerIFunc:<<47>>vjo.<<48>>NEEDS_IMPL
    })
    .<<49>>inits(function(){
		<<50>>this.<<51>>vj$.<<52>>InnerInterface.<<53>>innerIProp=<<54>>this.<<55>>vj$.<<56>>CTypeWithIType.<<57>>outerStaticFunc().<<58>>big();
		this.vj$.InnerInterface.innerIProp1=this.vj$.CTypeWithIType.static<<59>>OuterProp.<<60>>big();
    })
    .<<61>>endType(),
    
	InnerCType:<<62>>vjo.<<63>>ctype() //< public
	.<<64>>satisfies('<<65>>innert<<66>>ypes.CTypeWithIType.<<67>>InnerInterface')
	.<<68>>satisfies('<<69>>innertypes.CTypeWithIType.InnerStaticInterface')
	.<<70>>protos({
	    //> public void innerIFunc()
	    innerISFunc:function(){
	    	<<71>>var s=<<72>>this.<<73>>vj$.<<74>>InnerInterface.<<75>>innerIProp1; //<String
            var s1=this.vj$.CTypeWithIType.<<76>>InnerStaticInterface.<<77>>innerISProp; //<String
	    },
	    //> public void innerIFunc1()
	    innerIFunc:function(){
	    }
	})
	.<<78>>endType()
})
.<<79>>endType();