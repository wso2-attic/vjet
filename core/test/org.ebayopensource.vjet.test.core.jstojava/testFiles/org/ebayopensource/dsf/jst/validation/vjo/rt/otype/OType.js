vjo.otype("org.ebayopensource.dsf.jst.validation.vjo.rt.otype.OType")
.defs({
	NVPair:{
		name:null,//<String 
		value:null,//<int?
		callback: vjo.NEEDS_IMPL//<void callback(Date)
	},
	
	play: vjo.NEEDS_IMPL//<void play(OType.NVPair)
})
.endType();