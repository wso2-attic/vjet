vjo.itype("syntax.generic.Collection<E>")
//>needs(syntax.generic.Iterator)
.protos({
	
	//> public boolean containsAll(Collection<?> c)
	containsAll: vjo.NEEDS_IMPL,
	
	//> public boolean removeAll(Collection<?> c)
    removeAll: vjo.NEEDS_IMPL,
    
    //> public Iterator<E> iterator()
    iterator: vjo.NEEDS_IMPL
})
.endType();