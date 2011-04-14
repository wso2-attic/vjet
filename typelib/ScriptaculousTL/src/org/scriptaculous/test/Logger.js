/**
 * 
 */
vjo.ctype('org.scriptaculous.test.Logger') //< public

.props({
	/**
	 * 
	 */
	//> public void start(String testName)
	start: vjo.NEEDS_IMPL,
	
	/**
	 * 
	 */
	//> public void finish(String status, String summary)
	finish: vjo.NEEDS_IMPL,
	
	/**
	 * 
	 */
	//> public void message(String message)
	message: vjo.NEEDS_IMPL,
	
	/**
	 * 
	 */
	//> public void summary(String summary)
	summary: vjo.NEEDS_IMPL,
	
	/**
	 * 
	 */
	//> public void addLinksToResults()
	addLinksToResults: vjo.NEEDS_IMPL
})
.protos({
	/**
	 * 
	 */
	//> public constructs(Object log)
	constructs: function() {}
})
.options({
	metatype: true
})
.endType();