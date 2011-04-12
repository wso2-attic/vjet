vjo.itype('syntax.generic.GenericIType<E>') //< public
//>needs(syntax.generic.ArrayList)
.props({
  
})
.protos({
	 //>public void foo(ArrayList<String> sss)    
    foo : vjo.NEEDS_IMPL,
	
	//>public void refFoo(int i, E ref, boolean flag) 
	refFoo : vjo.NEEDS_IMPL
})
.endType();