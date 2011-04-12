vjo.ctype('innertypes.CTypeWithETypeTest') //< public
.props({
    staticOuterProp:"Test", //< String
    
    InnerStaticEnum:<<0>>vjo.<<1>>etype() //< public
    .<<2>>p<<3>>rops({
    	InnitialState : undefined, //< public final
        //> public void innerStaticFunc()
        innerStaticFunc:function(){
        	<<4>>var cl=<<5>>new <<6>>this.<<7>>vj$.<<8>>CTypeWithEType(); //<CTypeWithEType
        	<<9>>var inner=<<10>>this.<<124>>vj$.CTypeWithEType.<<11>>InnerStaticEnum.<<12>>d1; //<InnerStaticEnum
        	<<13>>vjo.<<14>>sysout.<<15>>println(<<16>>inner.<<17>>innerProp1);
			vjo.sysout.println(<<18>>cl.<<19>>outerProp);
			<<20>>vjo.sysout.println(<<21>>this.<<22>>vj$.<<23>>CTypeWithEType.<<24>>staticOuterProp);
        }
    })
    .<<25>>protos({
        outerType:null, //< CTypeWithEType
        innerProp1:"test", //< String
        innerProp2:null, //< String
        innerProp3:null, //< String
        innerProp4:null, //< String
        innerProp5:null, //< String
        innerProp6:null, //< String
        //> private constructs()
        constructs:function(){
        	<<26>>this.<<27>>outerType=<<28>>new <<29>>this.<<30>>vj$.<<31>>CTypeWithEType();
            this.<<32>>innerProp2=this.<<33>>innerProp1.<<34>>big();
            this.innerProp3=this.outerType.<<35>>outerProp.<<36>>big();
            this.innerProp4=this.<<37>>vj$.<<38>>CTypeWithEType.<<39>>staticOuterProp.<<40>>big();
            this.innerProp5=this.outerType.outerFunc1();
            <<41>>this.innerProp6=this.vj$.CTypeWithEType.<<42>>outerStaticFunc();
        },
        //> public void innerFunc(int i)
        //> public void innerFunc(String str)
        innerFunc:function(i){
        	<<43>>if(<<44>>arguments.<<45>>length===<<46>>1){
        		<<47>>if(<<48>>typeof <<49>>arguments[0]==<<50>>"number"){
        			<<51>>this.<<52>>innerFunc_1_0_InnerStaticEnum_ovld(<<53>>arguments[0]);
                }else if(<<54>>arguments[0] instance<<55>>of Str<<56>>ing || ty<<57>>peof <<58>>arguments[0]=="string"){
                    this.innerFunc_1_1_InnerStaticEnum_ovld(arguments[0]);
                }else if(<<59>>this.<<60>>base && this.base.<<61>>innerFunc){
                    this.base.<<62>>innerFunc.<<63>>apply(<<64>>this,<<65>>arguments);
                }
            }else if(this.base && this.base.innerFunc){
                this.base.innerFunc.apply(this,arguments);
            }
        },
        //> private void innerFunc_1_0_InnerStaticEnum_ovld(int i)
        innerFunc_1_0_InnerStaticEnum_ovld:function(i){
        	<<66>>var ot=<<67>>new this.vj$.<<68>>CTypeWithEType(); //<CTypeWithEType
        	<<69>>vjo.<<70>>sysout.<<71>>println(<<72>>this.<<73>>innerProp1);
            vjo.sysout.println(ot.<<74>>outerProp);
            vjo.sysout.println(this.vj$.CTypeWithEType.<<75>>staticOuterProp);
        },
        //> private void innerFunc_1_1_InnerStaticEnum_ovld(String str)
        innerFunc_1_1_InnerStaticEnum_ovld:function(str){
            var ot=new this.vj$.CTypeWithEType(); //<CTypeWithEType
            vjo.sysout.println(this.innerProp1);
            vjo.sysout.println(<<76>>ot.<<77>>outerProp);
            vjo.sysout.println(this.vj$.CTypeWithEType.staticOuterProp);
        }
    })
    .values('d1, d2, d3')
    .<<127>>inits( function() {
        this.InnitialState = <<125>>this.<<126>>d1 ;
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
    	var en1 = <<78>>this.vj$.CTypeWithEType.<<79>>InnerStaticEnum.<<80>>d1;//<InnerStaticEnum
    	<<81>>en1.<<82>>innerFunc(10);
    	var en2 = this.vj$.<<83>>InnerEnum.<<84>>d1;//<InnerEnum
    	en2.<<85>>innerFunc(10);
    },
    
	InnerEnum:<<86>>vjo.<<87>>etype() //< public
	.<<88>>props({
	    //> public void innerStaticFunc()
	    innerStaticFunc:function(){
	        var cl=<<89>>new this.vj$.<<90>>CTypeWithEType(); //<CTypeWithEType
	        var inner=<<91>>this.vj$.<<92>>InnerEnum.<<93>>d1; //<InnerEnum
	        vjo.sysout.println(<<94>>inner.<<95>>innerProp1);
	        vjo.sysout.println(cl.<<96>>outerProp);
	        vjo.sysout.println(this.vj$.<<97>>CTypeWithEType.<<98>>staticOuterProp);
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
	        this.outerType=<<99>>new <<100>>this.vj$.<<101>>CTypeWithEType();
	        this.innerProp2=this.<<102>>innerProp1;
	        this.innerProp3=this.outerType.<<103>>outerProp;
	        this.innerProp4=this.vj$.CTypeWithEType.<<104>>staticOuterProp;
	        this.innerProp5=this.outerType.outerFunc1();
	        this.innerProp6=this.vj$.CTypeWithEType.outerStaticFunc();
	    },
	    //> public void innerFunc(int i)
	    //> public void innerFunc(String str)
	    innerFunc:function(i){
	        if(<<105>>arguments.length===1){
	            if(typeof arguments[0]=="number"){
	                this.<<106>>innerFunc_1_0_InnerEnum_ovld(<<107>>arguments[0]);
	            }else if(arguments[0] ins<<108>>tanceof Stri<<109>>ng || ty<<110>>peof <<111>>arguments[0]=="string"){
	                this.innerFunc_1_1_InnerEnum_ovld(arguments[0]);
	            }else if(this.<<112>>base && this.base.<<113>>innerFunc){
	                this.base.innerFunc.apply(this,arguments);
	            }
	        }else if(this.base && this.base.innerFunc){
	            this.base.innerFunc.apply(this,arguments);
	        }
	    },
	    //> private void innerFunc_1_0_InnerEnum_ovld(int i)
	    innerFunc_1_0_InnerEnum_ovld:function(i){
	        var ot=<<114>>new <<115>>this.vj$.<<116>>CTypeWithEType(); //<CTypeWithEType
	        vjo.sysout.println(<<117>>this.<<118>>innerProp1);
	        vjo.sysout.println(ot.<<119>>outerProp);
	        vjo.sysout.println(this.vj$.CTypeWithEType.<<120>>staticOuterProp);
	    },
	    //> private void innerFunc_1_1_InnerEnum_ovld(String str)
	    innerFunc_1_1_InnerEnum_ovld:function(str){
	        var ot=new this.vj$.CTypeWithEType(); //<CTypeWithEType
	        vjo.sysout.println(this.innerProp1);
	        vjo.sysout.println(ot.outerProp);
	        vjo.sysout.println(this.vj$.CTypeWithEType.staticOuterProp);
	    }
	})
	.<<121>>values('d1, d2, d3')
	.<<122>>endType()
})
.<<123>>endType();