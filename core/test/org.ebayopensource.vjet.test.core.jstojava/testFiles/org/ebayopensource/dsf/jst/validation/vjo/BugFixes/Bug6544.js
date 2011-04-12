vjo.itype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug6544")
.props({
	value: 1,
	
	innerIType: vjo.itype()
	.props({
		innerProp: 20 //<public final
	})
	.endType()
})
.endType();