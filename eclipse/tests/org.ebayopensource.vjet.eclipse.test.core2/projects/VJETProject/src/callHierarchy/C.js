vjo.ctype("callHierarchy.C")
.props({
	//> public String a(String t)
	a : function(t) {
		
	},
	
	test : function() {
		this.a("test");
	}
})
.protos({
	foo : function() {
		this.vj$.C.a("foo");
	}
})
.endType();