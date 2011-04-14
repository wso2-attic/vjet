/**
 * 
 */
//> public
vjo.mtype('org.ecmascript.es5.DateES5')
.props({
	// now() is already supported
})
.protos({
	/**
	 * 
	 */
	//> public String toISOString()
	toISOString: vjo.NEEDS_IMPL
})
.options({
	metatype: true
})
.endType();
