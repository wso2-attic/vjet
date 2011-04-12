vjo.ctype("fld1.A")
.props({
	stA : function() {
		window.alert("stA() function called");
	}
})
.protos({
	a : function() {
		window.alert("a() function called");
	}
})
.endType();