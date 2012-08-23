vjo.needs("selection.B")
vjo.ctype("selection.A")
.inherits("selection.B")
.props({
	stA : 0, //< int
	
	stFunc : function() {
	}
})
.protos({
	x : 0, //< int
	
	foo : function() {
		this.vj$.A.stA = 1;
		this.x = 1;
		this.vj$.A.stFunc();
	}
})
.endType();