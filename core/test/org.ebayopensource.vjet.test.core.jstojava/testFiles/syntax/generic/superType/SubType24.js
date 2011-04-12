vjo.ctype('syntax.generic.superType.SubType24') //< public
//>needs(syntax.generic.Set)
//>needs(syntax.generic.Collection)
.needs('syntax.generic.HashSet')
.props({  
})
.protos({
	
	//>public void test(Collection<String>) 
	test : function(c){
		var set = new this.vj$.HashSet()//<Set<String>
		var s1 = null;//<Collection<? extends SubType24>
		set.containsAll(c);
		set.containsAll(s1);
	}
}) 
.endType();