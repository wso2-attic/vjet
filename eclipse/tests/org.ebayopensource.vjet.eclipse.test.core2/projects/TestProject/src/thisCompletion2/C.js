vjo.type("thisCompletion2.C")
.inherits("thisCompletion.B")
.protos({
	//> public thisCompletion.A fooC()
	fooC : function() {
		return new A();
	},
	
	//> public void doC()
	doC : function() {
		this.fooC().fooA();
	}
}); 