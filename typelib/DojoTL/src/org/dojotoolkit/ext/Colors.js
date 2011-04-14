vjo.ctype('org.dojotoolkit.ext.Colors') //< public
//>needs(org.dojotoolkit.Dojo)
.globals({
	colors : undefined //<type::Colors
}, dojo)
.props({
	//>public Dojo.Color makeGrey(Number g, Number? a)
	makeGrey : vjo.NEEDS_IMPL
})
.inits(function(){
	dojo.require("dojo.colors");
})
.options({
	metatype:true
})
.endType();