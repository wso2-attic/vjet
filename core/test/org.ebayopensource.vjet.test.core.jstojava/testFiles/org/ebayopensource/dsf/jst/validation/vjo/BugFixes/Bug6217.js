vjo.otype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug6217")
.defs({
	prp: -1, //<int
	
	Point: {
		x: 0,
		y: 0
	},
	
	fun: vjo.NEEDS_IMPL,
	
	fun2: function(){
		return false;
	},
	
	DoIt: vjo.ctype()
	.protos({
		
	}).endType()
})
.endType()
.endType();