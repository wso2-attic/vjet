vjo.atype("foo.A1")
.inherits("foo.A2")
.protos({
	//> public void foo()
	foo : function() {
		this.doIt();
	}
});