vjo.type("thisCompletion.B")
.inherits("thisCompletion.A")
.props({
	//> public void stB()
	stB: function() {
	}
})
.protos({
	b : 1, //< private int
	
	//>public  void fooB()
	fooB : function () {
		this.fooA();
	}
});