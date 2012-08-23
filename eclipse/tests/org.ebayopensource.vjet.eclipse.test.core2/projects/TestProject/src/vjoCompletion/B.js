vjo.needs("vjoCompletion.A");
vjo.type("vjoCompletion.B")
.props({
	stB : 5,
	
	//>public  void stDoIt()
	stDoIt : function () {}
})
.protos({
	//>public  void fooB()
	fooB : function () {
		vjo.$ns(this).A.stDoSomething().fooA();
	}
});