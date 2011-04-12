vjo.ctype('typecheck.compartible.typecheckCompartibleType1') //< public
.protos({


	 x : 10,  //< public int

	 s : 10,  //< public int

	 c : undefined, //< public String

	//> public String
     d : undefined,

    //> Number a()
    a:function(){
        
        this.x ="String";
        
        this.s = 'String';
        
        this.c = 322;//<<String
        
        this.d = 20;//<<String
    }
    
})
.endType();