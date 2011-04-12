vjo.ctype('innertypes.CTypeWithEType') //< public
.props({
    staticOuterProp:"Test", //< String
    
    InnerStaticEnum:vjo.etype() //< public
    .props({
    	InnitialState : undefined, //< public final
        //> public void innerStaticFunc()
        innerStaticFunc:function(){
        	var cl=new this.vj$.CTypeWithEType(); //<CTypeWithEType
        	var inner=this.vj$.CTypeWithEType.InnerStaticEnum.d1; //<InnerStaticEnum
        	vjo.sysout.println(inner.innerProp1);
			vjo.sysout.println(cl.outerProp);
			vjo.sysout.println(this.vj$.CTypeWithEType.staticOuterProp);
        }
    })
    .protos({
        outerType:null, //< CTypeWithEType
        innerProp1:"test", //< String
        innerProp2:null, //< String
        innerProp3:null, //< String
        innerProp4:null, //< String
        innerProp5:null, //< String
        innerProp6:null, //< String
        //> private constructs()
        constructs:function(){
        	this.outerType=new this.vj$.CTypeWithEType();
            this.innerProp2=this.innerProp1.big();
            this.innerProp3=this.outerType.outerProp.big();
            this.innerProp4=this.vj$.CTypeWithEType.staticOuterProp.big();
            this.innerProp5=this.outerType.outerFunc1();
            this.innerProp6=this.vj$.CTypeWithEType.outerStaticFunc();
        },
        //> public void innerFunc(int i)
        //> public void innerFunc(String str)
        innerFunc:function(i){
        	if(arguments.length===1){
        		if(typeof arguments[0]=="number"){
        			this.innerFunc_1_0_InnerStaticEnum_ovld(arguments[0]);
                }else if(arguments[0] instanceof String || typeof arguments[0]=="string"){
                    this.innerFunc_1_1_InnerStaticEnum_ovld(arguments[0]);
                }else if(this.base && this.base.innerFunc){
                    this.base.innerFunc.apply(this,arguments);
                }
            }else if(this.base && this.base.innerFunc){
                this.base.innerFunc.apply(this,arguments);
            }
        },
        //> private void innerFunc_1_0_InnerStaticEnum_ovld(int i)
        innerFunc_1_0_InnerStaticEnum_ovld:function(i){
        	var ot=new this.vj$.CTypeWithEType(); //<CTypeWithEType
        	vjo.sysout.println(this.innerProp1);
            vjo.sysout.println(ot.outerProp);
            vjo.sysout.println(this.vj$.CTypeWithEType.staticOuterProp);
        },
        //> private void innerFunc_1_1_InnerStaticEnum_ovld(String str)
        innerFunc_1_1_InnerStaticEnum_ovld:function(str){
            var ot=new this.vj$.CTypeWithEType(); //<CTypeWithEType
            vjo.sysout.println(this.innerProp1);
            vjo.sysout.println(ot.outerProp);
            vjo.sysout.println(this.vj$.CTypeWithEType.staticOuterProp);
        }
    })
    .values('d1, d2, d3')
    .inits( function() {
        this.InnitialState = this.d1 ;
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
    	var en1 = this.vj$.CTypeWithEType.InnerStaticEnum.d1;//<InnerStaticEnum
    	en1.innerFunc(10);
    	var en2 = this.vj$.InnerEnum.d1;//<InnerEnum
    	en2.innerFunc(10);
    },
    
	InnerEnum:vjo.etype() //< public
	.props({
	    //> public void innerStaticFunc()
	    innerStaticFunc:function(){
	        var cl=new this.vj$.CTypeWithEType(); //<CTypeWithEType
	        var inner=this.vj$.InnerEnum.d1; //<InnerEnum
	        vjo.sysout.println(inner.innerProp1);
	        vjo.sysout.println(cl.outerProp);
	        vjo.sysout.println(this.vj$.CTypeWithEType.staticOuterProp);
	    }
	})
	.protos({
	    outerType:null, //< CTypeWithEType
	    innerProp1:"test", //< String
	    innerProp2:null, //< String
	    innerProp3:null, //< String
	    innerProp4:null, //< String
	    innerProp5:null, //< String
	    innerProp6:null, //< String
	    //> private constructs()
	    constructs:function(){
	        this.outerType=new this.vj$.CTypeWithEType();
	        this.innerProp2=this.innerProp1;
	        this.innerProp3=this.outerType.outerProp;
	        this.innerProp4=this.vj$.CTypeWithEType.staticOuterProp;
	        this.innerProp5=this.outerType.outerFunc1();
	        this.innerProp6=this.vj$.CTypeWithEType.outerStaticFunc();
	    },
	    //> public void innerFunc(int i)
	    //> public void innerFunc(String str)
	    innerFunc:function(i){
	        if(arguments.length===1){
	            if(typeof arguments[0]=="number"){
	                this.innerFunc_1_0_InnerEnum_ovld(arguments[0]);
	            }else if(arguments[0] instanceof String || typeof arguments[0]=="string"){
	                this.innerFunc_1_1_InnerEnum_ovld(arguments[0]);
	            }else if(this.base && this.base.innerFunc){
	                this.base.innerFunc.apply(this,arguments);
	            }
	        }else if(this.base && this.base.innerFunc){
	            this.base.innerFunc.apply(this,arguments);
	        }
	    },
	    //> private void innerFunc_1_0_InnerEnum_ovld(int i)
	    innerFunc_1_0_InnerEnum_ovld:function(i){
	        var ot=new this.vj$.CTypeWithEType(); //<CTypeWithEType
	        vjo.sysout.println(this.innerProp1);
	        vjo.sysout.println(ot.outerProp);
	        vjo.sysout.println(this.vj$.CTypeWithEType.staticOuterProp);
	    },
	    //> private void innerFunc_1_1_InnerEnum_ovld(String str)
	    innerFunc_1_1_InnerEnum_ovld:function(str){
	        var ot=new this.vj$.CTypeWithEType(); //<CTypeWithEType
	        vjo.sysout.println(this.innerProp1);
	        vjo.sysout.println(ot.outerProp);
	        vjo.sysout.println(this.vj$.CTypeWithEType.staticOuterProp);
	    }
	})
	.values('d1, d2, d3')
	.endType()
})
.endType();
