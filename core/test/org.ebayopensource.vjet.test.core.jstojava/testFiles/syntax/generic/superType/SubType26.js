vjo.ctype('syntax.generic.superType.SubType26<E>') //< public
//>needs(Error)
.props({
	
})
.protos({
	//>public void test() 
	test : function(){
		var s = null;//<Error
		s.toString();  
	}
})
.endType();