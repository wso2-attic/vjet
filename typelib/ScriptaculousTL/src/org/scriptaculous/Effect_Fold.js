/**
 * Reduce the element to its top then to left to make it disappear
 * <p>
 * Examples
 * <code>
 * Effect.Fold('id_of_element');
 * </code>
 * Notes
 * <p>
 * Works safely with most Block Elements, except tables.
 */
vjo.ftype('org.scriptaculous.Effect_Fold') //< public
//> needs(org.scriptaculous.Effect_Scale)
.props({
	/**
	 * 
	 */
	//> public Effect_Scale _invoke_({String | Element} idOrElement)
	_invoke_: function() { return null ; }
})
.options({
	metatype: true
})
.endType();