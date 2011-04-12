vjo.ctype('syntax.generic.superType.SubType32<E>') //< public
.inherits('syntax.generic.superType.SuperType1')
.props({
})
.protos({
	//>public constructs()
	constructs : function(){
		this.base();
	},
	
	 //>public void test() 
	 test : function(){
		 this.getCity(234);
	 },
	 
	 //>public SuperType1 getCity(String) 
	 getCity : function(s){
		 return null;
	 }
}) 
.endType();