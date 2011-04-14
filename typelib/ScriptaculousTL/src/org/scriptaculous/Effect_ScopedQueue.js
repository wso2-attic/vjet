/** from effects.js
 * 
 */
vjo.ctype('org.scriptaculous.Effect_ScopedQueue') //< public
//> needs(org.prototypejs.Enumerable)
//> needs(org.scriptaculous.Effect)
.mixin('org.prototypejs.Enumerable')
.props({
	
})
.protos({
	/**
	 * 
	 */
	//> public constructs()
	constructs: function() {},
	
	/**
	 * 
	 */
	//> public void _each(Object iterator)
	_each: vjo.NEEDS_IMPL,
	
	/**
	 * 
	 */
	//> public void add(Effect effect)
	add: vjo.NEEDS_IMPL,
	
	/**
	 * 
	 */
	//> public void remove(Effect effect)
	remove: vjo.NEEDS_IMPL,
	
	/**
	 * 
	 */
	//> public void loop()
	loop: vjo.NEEDS_IMPL
})
.options({
	metatype: true
})
.endType();