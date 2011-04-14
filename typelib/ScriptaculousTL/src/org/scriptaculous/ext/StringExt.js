/**
 * 
 */
vjo.mtype('org.scriptaculous.ext.StringExt') //< public

.props({
	/**
	 * 
	 */
	//> public HTMLDivElement
	__parseStyleElement: null
})
.protos({
	/** - from effect.js
	 * converts rgb() and #xxx to #xxxxxx format
	 * <p>
	 * returns self (or first argument) if not convertable
	 */
	//> public String parseColor(Object... args)
	parseColor: vjo.NEEDS_IMPL,
	
	/**
	 * 
	 */
	//> public Object parseStyle()
	parseStyle: vjo.NEEDS_IMPL
	
})
.endType();