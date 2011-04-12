vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug6351CType")
.satisfies("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug6351IType")
.protos({
	//>public void foo(String)
	//>public void foo()
	foo: function(){
	},
	
	//>public void bar()
	bar: function(){
	}
})
.endType();