vjo.ctype('syntax.generic.GenericRefType3') //< public
.needs('syntax.generic.GenericRefType1')
.needs('syntax.generic.TemplateRefType1')
.needs('syntax.generic.TemplateRefType2')
.protos({
	
	age : 30, //< public int

	//>public void foo(GenericRefType1<Number> ref1) 
	foo : function(ref1){
	
		ref1.refFoo(this.age, this.age, true);
		//ref1.refFoo(this.age, "ddd", true);
	},
	
	//>public void bar(GenericRefType1<TemplateRefType1> ref1)
	bar: function(ref1){
		var v = null;//<TemplateRefType1
		v = ref1.foo(v);
	},
	
	//>public void mix(GenericRefType1<? extends TemplateRefType2> ref1)
	mix: function(ref1){
		var v = null;//<TemplateRefType2
		v = ref1.foo(v);
		v.live();
		
		ref1.foo(v).live();
	}
})
.endType();