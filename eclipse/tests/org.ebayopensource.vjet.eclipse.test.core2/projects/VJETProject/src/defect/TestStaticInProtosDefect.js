vjo.ctype("defect.TestStaticInProtosDefect")
.needs("defect.A")
.inherits("defect.A")
.props({
	stA : 0, //< int
	
	stFunc : function() {
		var x = 1; //< A
		x.ggg();
	}
})
.protos({
	foo : function() {
		st
	}
})
.endType();