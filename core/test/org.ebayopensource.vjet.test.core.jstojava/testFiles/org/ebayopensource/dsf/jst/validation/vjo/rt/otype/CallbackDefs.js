vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.rt.otype.CallbackDefs")
.props({
	whenConnect: vjo.otype().defs({
		onConnect: vjo.NEEDS_IMPL,//<void onConnect(String, int)
	}),
	
	whenDisconnect: vjo.otype().defs({
		onDisconnect: vjo.NEEDS_IMPL//<void onDisconnect(String?, Date?)
	})
})
.options({
	metatype: true
})
.endType();