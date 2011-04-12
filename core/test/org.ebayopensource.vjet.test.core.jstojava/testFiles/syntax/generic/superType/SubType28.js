vjo.ctype('syntax.generic.superType.SubType28') //< public
//>needs(syntax.generic.Collection)
//>needs(syntax.generic.ArrayList)
//>needs(syntax.generic.AbstractList)
.props({  
})
.protos({
	
	//>public void test(Collection<? super ArrayList>) 
	test : function(c){
		var s1 = null;//<vjo.Object         
		this.sort(c);         
	},              
	
	//>public void sort(Collection<? extends Object>) 
	sort : function(c){
		    
	}
}) 
.endType();