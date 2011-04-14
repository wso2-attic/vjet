/**
 * 
 */
vjo.ctype('org.scriptaculous.Effect_Parallel') //< public
//> needs(org.scriptaculous.Effect_Base)
.inherits('org.scriptaculous.Effect_Base')
.props({
	
})
.protos({
	/**
	 * 
	 */
	//> public constructs(Object effects)
	constructs: function() {},
	
	/**
	 * 
	 */
	//> public void update(Number position)
	update: vjo.NEEDS_IMPL,
	
	/**
	 * 
	 */
	//> public void finish()
	finish: vjo.NEEDS_IMPL
})
.options({
	metatype: true
})
.endType();