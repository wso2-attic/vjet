vjo.ctype('innertypes.OuterTypeTest') //< public
.props({
    staticOuterProp:"Test", //< String
    //> public String outerStaticFunc()
    outerStaticFunc:function(){
    	<<0>>vjo.<<1>>sysout.<<2>>println(<<3>>this.<<4>>staticOuterProp);
        return <<5>>"";
    }
})
.protos({
    outerProp:"test", //< String
    InnerType:<<6>>vjo.<<7>>ctype() //< public
    .<<8>>p<<9>>rotos({
        innerProp1:"test", //< String
        innerProp2:<<10>>null, //< String
        innerProp3:<<11>>null, //< String
        innerProp4:null, //< String
        innerProp5:null, //< String
        innerProp6:null, //< String
        //> private constructs()
        constructs:function(){
        	this.<<12>>innerProp2=<<13>>this.<<14>>innerProp1;
        	<<15>>this.<<16>>innerProp3=<<17>>this.<<18>>vj$.<<19>>outer.<<20>>outerProp.<<21>>big();
            this.innerProp4=<<22>>this.<<23>>vj$.<<24>>OuterType.<<25>>staticOuterProp.<<26>>big();
            this.innerProp5=this.vj$.outer.<<27>>outerFunc1();
            this.innerProp6=this.vj$.OuterType.<<28>>outerStaticFunc();
        },
        //> public void innerFunc()
        //> public void innerFunc(String str)
        innerFunc:function(){
        	<<29>>if(<<30>>arguments.<<31>>length===0){
        		<<32>>this.<<33>>innerFunc_0_0_InnerType_ovld();
            }else if(<<34>>arguments.<<35>>length===1){
            	<<36>>this.<<37>>innerFunc_1_0_InnerType_ovld(<<38>>arguments[0]);
            }
        },
        //> private void innerFunc_0_0_InnerType_ovld()
        innerFunc_0_0_InnerType_ovld:function(){
        	<<39>>vjo.<<40>>sysout.<<41>>println(<<42>>this.<<43>>innerProp1);
        	vjo.sysout.println(this.<<44>>vj$.<<45>>outer.<<46>>outerProp);
        	vjo.sysout.println(this.vj$.OuterType.<<47>>staticOuterProp);
        	vjo.sysout.println(this.vj$.OuterType.outerStaticFunc());
        },
        //> private void innerFunc_1_0_InnerType_ovld(String str)
        innerFunc_1_0_InnerType_ovld:function(str){
        	vjo.sysout.println(this.innerProp1);
        	vjo.sysout.println(this.vj$.outer.outerProp);
        	vjo.sysout.println(this.vj$.OuterType.staticOuterProp);
        	vjo.sysout.println(this.vj$.OuterType.outerStaticFunc());
        }
    })
    .<<48>>endType(),
    //> public String outerFunc1()
    outerFunc1:function(){
    	vjo.sysout.println(this.outerProp);
        return "";
    },
    //> public void outerFunc2()
    outerFunc2:function(){
        var inner=new <<52>>this.<<49>>InnerType(); //<InnerType
        <<50>>inner.<<51>>innerFunc();
    }
})
.endType();