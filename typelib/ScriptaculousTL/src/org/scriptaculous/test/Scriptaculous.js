vjo.ctype('org.scriptaculous.test.Scriptaculous') //< public

.globals({
	Test: null	//< type::Scriptaculous
})
.props({
	/**
	 * 
	 */
	Unit: vjo.ctype() 	 //< public
	.props({
		/**
		 * 
		 */
		//> public String inspect(Object)
		inspect: vjo.NEEDS_IMPL,
		
		Assertions: null,//< public type::org.scriptaculous.test.Assertions
		Logger: null,	 //< public type::org.scriptaculous.test.Logger
		Runner: null,	 //< public type::org.scriptaculous.test.Runner
		Testcase: null	 //< public type::org.scriptaculous.test.TestCase
	})
	.endType()
})
.protos({
	
})
.endType();