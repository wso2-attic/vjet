vjo.ftype('org.dojotoolkit.ext.Cookie') //< public
.globals({
	cookie : undefined //<type::Cookie
}, dojo)
.props({
	//>public String cookie(String name)
	//>public void cookie(String name, String value, Cookie.__options.cookieProps? props)
	_invoke_ : vjo.NEEDS_IMPL,
	
	//>public boolean isSupported()
	isSupported : vjo.NEEDS_IMPL,
	
	__options : vjo.otype().defs({ //<public
		cookieProps : { //<public
			domain : null, //<public String?
			expires : null, //<public Object?
			path : null, //<public String?
			secure : null //<public boolean?	
		}
	}).endType()
})
.options({
	metatype:true
})
.endType();