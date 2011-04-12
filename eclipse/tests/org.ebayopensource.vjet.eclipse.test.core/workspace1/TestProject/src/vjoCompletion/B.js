vjo.type("vjoCompletion.B")
.needs("vjoCompletion.A")
.props({
	stB : 5,
	
	//>public  void stDoIt()
	stDoIt : function () {}
})
.protos({
	//>public  void fooB()
	fooB : function () {
		this.vj$.A.stDoSomething().fooA();
		this.vj$.B.stB = 0;
	}
});