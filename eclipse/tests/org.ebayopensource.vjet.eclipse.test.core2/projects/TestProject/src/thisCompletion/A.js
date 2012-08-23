vjo.type("thisCompletion.A")
.props({
	//> public void stA()
	stA: function() {
	}
})
.protos({
	a1 : 0, //< private int
	a2 : 5, //< public int
	
	//> public void fooA()
	fooA : function() {
		this.doA();
	},
	
	//> private void doA()
	doA: function() {
	}
	
});