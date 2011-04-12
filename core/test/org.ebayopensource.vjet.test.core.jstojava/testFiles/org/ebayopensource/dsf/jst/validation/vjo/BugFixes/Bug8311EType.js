vjo.etype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8311EType')
.values("a, b, c")
.props({
	x: -1, //< protected final int
	s: 'nope', //< final String
	o: new Object() //< final Object
})
.endType();