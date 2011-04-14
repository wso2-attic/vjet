/**
 * 
 */
vjo.ctype('org.scriptaculous.Effect_Morph') //< public
//> needs(org.scriptaculous.Effect_Base)
.inherits('org.scriptaculous.Effect_Base')
.props({
	
})
.protos({
	/**
	 * 
	 */
	//> public constructs({String | Element} idOrElement)
	constructs: function() {},
	
	/**
	 * 
	 */
	//> public void setup()
	setup: vjo.NEEDS_IMPL,
	
	/**
	 * 
	 */
	//> public void update(Number position)
	update: vjo.NEEDS_IMPL
})
.options({
	metatype: true
})
.endType();