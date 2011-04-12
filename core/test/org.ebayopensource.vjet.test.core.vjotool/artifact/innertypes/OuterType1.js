vjo.ctype('innertypes.OuterType1') //< public
.props({
    staticOuterProp:"Test", //< String
    InnerType:vjo.ctype() //< public
    .props({
        //> public void innerStaticFunc()
        innerStaticFunc:function(){
        	var cl=new this.vj$.OuterType1(); //<OuterType1
            var inner=new this(); //<InnerType
            vjo.sysout.println(inner.innerProp1);
            vjo.sysout.println(cl.outerProp);
            vjo.sysout.println(this.vj$.OuterType1.staticOuterProp);
        }
    })
    .protos({
        outerType:null, //< OuterType1
        innerProp1:"test", //< String
        innerProp2:null, //< String
        innerProp3:null, //< String
        innerProp4:null, //< String
        innerProp5:null, //< String
        innerProp6:null, //< String
        //> private constructs()
        constructs:function(){
        	this.outerType=new this.vj$.OuterType1();
            this.innerProp2=this.innerProp1;
            this.innerProp3=this.outerType.outerProp.big();
            this.innerProp4=this.vj$.OuterType1.staticOuterProp.big();
            this.innerProp5=this.outerType.outerFunc1();
            this.innerProp6=this.vj$.OuterType1.outerStaticFunc();
        },
        //> public void innerFunc()
        //> public void innerFunc(String str)
        innerFunc:function(){
        	if(arguments.length===0){
        		this.innerFunc_0_0_InnerType_ovld();
            }else if(arguments.length===1){
                this.innerFunc_1_0_InnerType_ovld(arguments[0]);
            }
        },
        //> private void innerFunc_0_0_InnerType_ovld()
        innerFunc_0_0_InnerType_ovld:function(){
            var ot=new this.vj$.OuterType1(); //<OuterType1
            vjo.sysout.println(this.innerProp1);
            vjo.sysout.println(ot.outerProp);
            vjo.sysout.println(this.vj$.OuterType1.staticOuterProp);
        },
        //> private void innerFunc_1_0_InnerType_ovld(String str)
        innerFunc_1_0_InnerType_ovld:function(str){
            var ot=new this.vj$.OuterType1(); //<OuterType1
            vjo.sysout.println(this.innerProp1);
            vjo.sysout.println(ot.outerProp);
            vjo.sysout.println(this.vj$.OuterType1.staticOuterProp);
        }
    })
    .endType(),
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
        var inner=new this.vj$.OuterType1.InnerType(); //<InnerType
        inner.innerFunc();
    }
})
.endType();
