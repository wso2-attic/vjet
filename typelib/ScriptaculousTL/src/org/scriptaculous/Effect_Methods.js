/**
 * This mixin is mixed into Element via Element.addMethods(Effect.Methods)
 * from effect.js
 */
vjo.mtype('org.scriptaculous.Effect_Methods') //< public

.props({
	
})
.protos({
	/**
	 * 
	 */
	//> public Element morph({String | Element} idOrElement, String style)
	morph: vjo.NEEDS_IMPL,
	
	/**
	 * 
	 */
	//> public Element visualEffect({String | Element} idOrElement, String effect, Object options)
	visualEffect: vjo.NEEDS_IMPL,
	
	/**
	 * 
	 */
	//> public Element highlight({String | Element} idOrElement, Object options)
	highlight: vjo.NEEDS_IMPL
})
.endType();