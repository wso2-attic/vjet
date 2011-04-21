vjo.ctype('org.json.JSON') //< public
.globals({
	JSON : undefined //<<type::JSON
})
.props({
	//> public String parse(String text, JSON.objects::reviver? reviver)
	parse:vjo.NEEDS_IMPL,
	
	//> private
	objects: vjo.otype()
	.defs({
		//> public Object fn(String key, Object value)
		reviver: vjo.NEEDS_IMPL,
		//> public Object fn(String key, Object value)
		replacer: vjo.NEEDS_IMPL
	}).endType(),
	
	//> public String stringify(Object value, JSON.objects::replacer? replacer, String? space)
	stringify:vjo.NEEDS_IMPL

})
.options({
	metatype:true
})
.endType();