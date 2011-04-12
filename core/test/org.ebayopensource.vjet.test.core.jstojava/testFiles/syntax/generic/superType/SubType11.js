vjo.ctype('syntax.generic.superType.SubType11<E>') //< public
//> needs(syntax.generic.Collection)
.inherits('syntax.generic.superType.SuperType1<E>')
.props({
})
.protos({
	//>public constructs()
	constructs : function(){
		this.base();
	},
	
	//>public void test() 
	test : function(){
	},
	
	//>public Collection<? extends SuperType1> getName(SuperType1)
	getName : function(p){
		return null;
	},
	
	 //>public SuperType1 getCity(SuperType1<? extends SuperType1>) 
	 getCity : function(p){
		 return null;
	   }
})
.endType();