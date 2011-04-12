vjo.ctype('syntax.generic.superType.SubType16<E>') //< public
.needs('syntax.generic.ArrayList')
.inherits('syntax.generic.superType.SuperType1<E>')
.props({
})
.protos({
	
	//>public void foo() 
	foo : function(){
		
	},
	
	//>public void test() 
	test : function(){
		var s = new this.vj$.ArrayList();//<ArrayList<SubType16>
		s.iterator().next().foo();
	}
}) 
.endType();