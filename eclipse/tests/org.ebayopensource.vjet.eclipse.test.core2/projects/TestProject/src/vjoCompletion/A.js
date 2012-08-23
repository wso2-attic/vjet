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
		vjo.$static(this).doSomething();
		vjo.$static(this).stA = 1;
	}
	,
	//> public void func()
	func: function() {
	}
});
