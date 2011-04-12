vjo.otype("org.ebayopensource.dsf.jst.validation.vjo.rt.otype.OTypeNested")
.defs({
	Enclosed: vjo.otype().defs({
		NestedNV : {
			label: null,//<String
			print: vjo.NEEDS_IMPL//<void print()
		},
		
		nestedPlay : vjo.NEEDS_IMPL//<void nestedPlay(OTypeNested.Enclosed.NestedNV)
	}).endType(),
	
	NV: {
		label: null,//<String?
		print: vjo.NEEDS_IMPL//<(void print())?
	}
})
.endType();