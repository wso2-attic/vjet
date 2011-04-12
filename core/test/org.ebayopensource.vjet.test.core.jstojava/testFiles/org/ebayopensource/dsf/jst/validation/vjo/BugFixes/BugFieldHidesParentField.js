vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.BugFieldHidesParentField")
.protos({
	foo: 100 //< int
})
.props({
	inner: vjo.ctype()
		.inherits("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.BugFieldHidesParentField")
		.protos({
			foo: 1 //< int
		})
		.endType()
}).endType();