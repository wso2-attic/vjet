vjo.ctype('syntax.typeCast.GlobalCast') //< public
//> needs(syntax.generic.Collection)
//> needs(syntax.generic.Iterator)
.props({
	
})
.protos({
	//>public void foo() 
	foo : function(){
		var s = null;//<Collection
		var si = s.iterator(); //<<Iterator
		var sww = si.next();//<<String  
		var sww = si.next();//<<
	}
})
.endType();