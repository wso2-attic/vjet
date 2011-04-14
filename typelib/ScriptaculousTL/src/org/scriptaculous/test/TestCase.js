/**
 * 
 */
vjo.ctype('org.scriptaculous.test.TestCase') //< public

.props({
	
})
.protos({
	/**
	 * 
	 */
	//> public constructs(String name, Object test, Function? setup, Function? teardown)
	constructs: function() {},
	
	/**
	 * 
	 */
	//> public void wait(Number time, Object nextPart)
	wait: vjo.NEEDS_IMPL,
	
	/**
	 * 
	 */
	//> public void run()
	run: vjo.NEEDS_IMPL
})
.options({
	metatype: true
})
.endType();