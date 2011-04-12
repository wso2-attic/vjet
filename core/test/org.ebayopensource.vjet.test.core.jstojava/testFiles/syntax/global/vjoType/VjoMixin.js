vjo.ctype('syntax.global.vjoType.VjoMixin') //< public
.props({
	//>public void foo2() 
	foo2 : function(){
		vjo.mixin("syntax.global.vjoType.VjoMType","syntax.global.vjoType.VjoMixin");
		this.smfoo();
	}
})
.protos({
	//>public void foo() 
	foo : function(){
		vjo.mixin("syntax.global.vjoType.VjoMType","syntax.global.vjoType.VjoMixin");
		this.mfoo();
	}
})
.endType();