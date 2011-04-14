vjo.ctype('JSONDef') //< public
.globals({
	JSON: null	//< type::JSONDef
})
.props({
	//> public String stringify(Object obj, Function? replacer)
	stringify: vjo.NEEDS_IMPL,
	
	//> public Object parse(String jsonText, Function? reviver)
	parse: vjo.NEEDS_IMPL
})
.protos({
	
})
.options({
	metatype: true
})
.endType();