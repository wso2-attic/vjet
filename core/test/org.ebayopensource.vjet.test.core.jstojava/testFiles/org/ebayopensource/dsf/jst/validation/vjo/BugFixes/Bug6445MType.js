vjo.mtype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug6445MType")
.expects("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug6445IType")
.protos({
	//> public void bar()
	bar: function(){
		this.foo("no error");
	}
})
.endType();