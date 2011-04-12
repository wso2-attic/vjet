vjo.ctype('innertypes.OuterType') //< public
.props({
    staticOuterProp:"Test", //< String
    //> public String outerStaticFunc()
    outerStaticFunc:function(){
    	vjo.sysout.println(this.staticOuterProp);
        return "";
    }
})
.protos({
    outerProp:"test", //< String
    InnerType:vjo.ctype() //< public
    .protos({
        innerProp1:"test", //< String
        innerProp2:null, //< String
        innerProp3:null, //< String
        innerProp4:null, //< String
        innerProp5:null, //< String
        innerProp6:null, //< String
        //> private constructs()
        constructs:function(){
        	this.innerProp2=this.innerProp1;
        	this.innerProp3=this.vj$.outer.outerProp.big();
            this.innerProp4=this.vj$.OuterType.staticOuterProp.big();
            this.innerProp5=this.vj$.outer.outerFunc1();
            this.innerProp6=this.vj$.OuterType.outerStaticFunc();
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
        	vjo.sysout.println(this.innerProp1);
        	vjo.sysout.println(this.vj$.outer.outerProp);
        	vjo.sysout.println(this.vj$.OuterType.staticOuterProp);
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
    .endType(),
    //> public String outerFunc1()
    outerFunc1:function(){
    	vjo.sysout.println(this.outerProp);
        return "";
    },
    //> public void outerFunc2()
    outerFunc2:function(){
        var inner=new this.InnerType(); //<InnerType
        inner.innerFunc();
    }
})
.endType();
