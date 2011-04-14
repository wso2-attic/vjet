/**
 * 
 */
vjo.ctype('org.scriptaculous.Effect') //< public

.props({
	_elementDoesNotExistError: null //< public Object
})
.protos({
	/**
	 * 
	 */
	//> public void tagifyText({String | Element} idOrElement)
	tagifyText: vjo.NEEDS_IMPL,
	
	/**
	 * 
	 */
	//> public void multiple({String | Element} idOrElement, Effect effect)
	multiple: vjo.NEEDS_IMPL,
	
//	  PAIRS: {
//	    'slide':  ['SlideDown','SlideUp'],
//	    'blind':  ['BlindDown','BlindUp'],
//	    'appear': ['Appear','Fade']
//	  },

	/**
	 * 
	 */
	//> public Effect toggle({String | Element} idOrElement, Effect effect, Object? options)
	toggle: vjo.NEEDS_IMPL

	
})
.options({
	metatype: true
})
.endType();