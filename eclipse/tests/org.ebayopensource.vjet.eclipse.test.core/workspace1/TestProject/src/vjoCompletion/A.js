vjo.type("vjoCompletion.A")
.props({
	stA : 15,
	
	//>public vjoCompletion.A stDoSomething()
	stDoSomething : function() {
		stA = 1;
	}
	
})
.protos({
	//>public void fooA()
	fooA : function() {
		this.vj$.A.stDoSomething();
		this.vj$.A.stA = 1;
	}
	,
	//> public void func()
	func: function() {
	}
});
