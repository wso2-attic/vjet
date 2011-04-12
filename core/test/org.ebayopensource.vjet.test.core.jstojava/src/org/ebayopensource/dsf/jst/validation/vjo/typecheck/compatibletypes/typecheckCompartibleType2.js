vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.typecheck.compatibletypes.typecheckCompartibleType2') //< public
.protos({

	test1 : undefined,  //<  public String
	test2 : undefined,  //<  public Date

    //> Number a()
    a:function(){
       this.test1 = 20;
       return this.test1;
    },
        
	b:function(){  //< public void b()
	 this.test2 = 30;
	},
	
    //> public void sgetBoolean()
	sgetBoolean : function(){
		var booleantest1; //< public boolean
		booleantest1 = 302;
	},
			
    //> public void sgetBoolean1()
	sgetBoolean1 : function(){
		//> public boolean
		var booleantest1; 
		booleantest1 = 32;
	}
})
.endType();