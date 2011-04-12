vjo.ctype('innertypes.OuterType1Test') //< public
.props({
    staticOuterProp:"Test", //< String
    InnerType:<<0>>vjo.<<1>>ctype() //< public
    .<<2>>props({
        //> public void innerStaticFunc()
        innerStaticFunc:function(){
        	<<3>>var cl=<<4>>new <<5>>this.<<6>>vj$.<<7>>OuterType1(); //<OuterType1
            var inner=<<8>>new <<9>>this(); //<InnerType
            <<10>>vjo.<<11>>sysout.<<12>>println(<<13>>inner.<<14>>innerProp1);
            vjo.sysout.println(<<15>>cl.<<16>>outerProp);
            vjo.sysout.println(this.<<17>>vj$.<<18>>OuterType1.<<19>>staticOuterProp);
        }
    })
    .<<20>>p<<21>>rotos({
        outerType:null, //< OuterType1
        innerProp1:"test", //< String
        innerProp2:null, //< String
        innerProp3:null, //< String
        innerProp4:null, //< String
        innerProp5:null, //< String
        innerProp6:null, //< String
        //> private constructs()
        constructs:function(){
        	<<22>>this.<<23>>outerType=<<24>>new <<25>>this.<<26>>vj$.<<27>>OuterType1();
            this.<<28>>innerProp2=this.<<29>>innerProp1;
            this.innerProp3=this.outerType.<<30>>outerProp.<<31>>big();
            this.innerProp4=this.vj$.<<32>>OuterType1.<<33>>staticOuterProp.<<34>>big();
            this.innerProp5=this.outerType.<<35>>outerFunc1();
            this.innerProp6=this.vj$.OuterType1.outerStaticFunc();
        },
        //> public void innerFunc()
        //> public void innerFunc(String str)
        innerFunc:function(){
        	<<36>>if(<<37>>arguments.<<38>>length===0){
        		<<39>>this.<<40>>innerFunc_0_0_InnerType_ovld();
            }else if(<<41>>arguments.<<42>>length===1){
                this.<<43>>innerFunc_1_0_InnerType_ovld(<<44>>arguments[0]);
            }
        },
        //> private void innerFunc_0_0_InnerType_ovld()
        innerFunc_0_0_InnerType_ovld:function(){
            var ot=new this.vj$.OuterType1(); //<OuterType1
            <<45>>vjo.<<46>>sysout.<<47>>println(<<48>>this.<<49>>innerProp1);
            vjo.sysout.println(<<50>>ot.<<51>>outerProp);
            vjo.sysout.println(this.vj$.OuterType1.<<52>>staticOuterProp);
        },
        //> private void innerFunc_1_0_InnerType_ovld(String str)
        innerFunc_1_0_InnerType_ovld:function(str){
            var ot=new this.vj$.<<53>>OuterType1(); //<OuterType1
            vjo.sysout.println(this.innerProp1);
            vjo.sysout.println(<<54>>ot.<<55>>outerProp);
            vjo.sysout.println(this.vj$.OuterType1.<<56>>staticOuterProp);
        }
    })
    .<<57>>endType(),
    //> public String outerStaticFunc()
    outerStaticFunc:function(){
    	vjo.sysout.println(this.staticOuterProp);
        return "";
    }
})
.protos({
    outerProp:"test", //< String
    //> public String outerFunc1()
    outerFunc1:function(){
    	vjo.sysout.println(this.outerProp);
        return "";
    },
    //> public void outerFunc2()
    outerFunc2:function(){
        var inner=new this.vj$.<<58>>OuterType1.<<59>>InnerType(); //<InnerType
        <<60>>inner.<<61>>innerFunc();
    }
})
.endType();