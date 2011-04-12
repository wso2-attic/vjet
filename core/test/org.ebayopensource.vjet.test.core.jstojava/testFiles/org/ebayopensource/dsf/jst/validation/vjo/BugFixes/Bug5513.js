vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5513")
.protos({
	//>public Object foo()
	foo: function(){
		alert(this.bar().getFullYear());
		return this;
	},
	
	//> public Object bar()
	bar: function(){
		return new Date();
	}
})
.endType();