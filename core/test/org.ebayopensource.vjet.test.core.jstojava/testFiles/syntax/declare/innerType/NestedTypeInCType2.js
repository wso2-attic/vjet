vjo.ctype('syntax.declare.innerType.NestedTypeInCType2') //< public

.props({
  
})
.protos({
	Inner1 : vjo.ctype()
	.props({
	})
	.protos({
	})
	.endType(),
	
	//>public Inner1 invoke(Inner1 inner) 
	invoke : function(inner){
	 	return inner;
	}
})
.endType();