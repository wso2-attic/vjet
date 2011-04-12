vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug6803CType")
.satisfies("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug6803IType")
.mixin("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug6803MType")
.props({
	main: function(){ //<public void main()
		var sample = this.vj$.Bug6803CType();//<Bug6803CType
		sample.exists();
	}
})
.endType();