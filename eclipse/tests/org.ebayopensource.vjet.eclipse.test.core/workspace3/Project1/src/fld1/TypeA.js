vjo.ctype("fld1.TypeA")
.props({
	stA : function() {
		window.alert("stA() function called");
	}
})
.protos({
	fooA : function() {
		window.alert("fooA() function called");
	}
})
.endType();