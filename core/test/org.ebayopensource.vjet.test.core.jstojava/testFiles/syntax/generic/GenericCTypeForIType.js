vjo.itype('syntax.generic.GenericCTypeForIType<E>') //< public
//> needs(syntax.generic.ArrayList)
.props({
  
})
.protos({
	//>public void foo(ArrayList<String> s) 
	foo : vjo.NEEDS_IMPL,
	
	//>public void refFoo(int i, E ref, boolean flag) 
	refFoo : vjo.NEEDS_IMPL
})
.endType();