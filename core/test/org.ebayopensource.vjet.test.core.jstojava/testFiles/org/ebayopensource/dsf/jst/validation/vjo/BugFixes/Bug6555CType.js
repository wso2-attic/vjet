vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug6555CType")
.satisfies("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug6555IType")
.protos({

	foo: function(){ //< void foo()
		alert('hello');
	},
	
	bar: function(){ //< public void bar()
		alert('hello');
	},
	
	bar2: function(){ //< void bar2()
		alert('world');
	}
})
.endType();