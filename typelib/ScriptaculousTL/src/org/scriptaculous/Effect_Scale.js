/**
 * 
 */
vjo.ctype('org.scriptaculous.Effect_Scale') //< public
//> needs(org.scriptaculous.Effect_Base)
.inherits('org.scriptaculous.Effect_Base')
.props({
	
})
.protos({
	/**
	 * 
	 */
	//> public constructs({String | Element} idOrElement, Number percent)
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
	update: vjo.NEEDS_IMPL,
	
	/**
	 * 
	 */
	//> public void finish(Number position)
	finish: vjo.NEEDS_IMPL,
	
	/**
	 * 
	 */
	//> public void setDimensions(int height, int width)
	setDimensions: vjo.NEEDS_IMPL
	
})
.options({
	metatype: true
})
.endType();