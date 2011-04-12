vjo.ctype('syntax.generic.superType.SubType25<T>') //< public
//>needs(syntax.generic.Set)
//>needs(syntax.generic.Collection)
.needs('syntax.generic.HashSet')
.props({  
})
.protos({
	
	//>public void test(Collection<String>) 
	test : function(c){
		var set = new this.vj$.HashSet()//<Set<String>
		var s1 = null;//<Collection<String>
		s1.removeAll(set);
	}
}) 
.endType();