vjo.ctype('syntax.generic.GenericRefType4') //< public

.protos({
	//>public <E> foo(E refE) 
	foo : function(refE){
	},
	
	//>public void refFoo(int i, boolean flag) 
	refFoo : function(i, flag){
		this.foo(i);
	 	this.foo(flag);
	}
})
.endType();