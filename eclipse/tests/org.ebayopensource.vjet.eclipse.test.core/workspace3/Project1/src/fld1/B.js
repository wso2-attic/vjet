vjo.ctype("fld1.B")
.props({
	stB : function() {
		window.alert("stB() function called");
	}
})
.protos({
	b : function() {
		window.alert("b() function called");
	}
})
.endType();