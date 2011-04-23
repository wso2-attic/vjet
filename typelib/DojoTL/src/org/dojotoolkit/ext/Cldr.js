vjo.ctype('org.dojotoolkit.ext.Cldr') //< public
.globals({
	cldr: undefined //<type::Cldr
}, dojo)
.props({	
	monetary : vjo.ctype().props({ //< public
		//>public Object getData(String code)
		getData : vjo.NEEDS_IMPL
	}).endType(),
	
	supplemental : vjo.ctype().props({ //< public
		//>public int getFirstDayOfWeek(String? locale)
		getFirstDayOfWeek : vjo.NEEDS_IMPL,

		//>public int getWeekend(String? locale)
		getWeekend : vjo.NEEDS_IMPL
	}).endType()
	
})
.options({
	metatype:true
})
.endType();