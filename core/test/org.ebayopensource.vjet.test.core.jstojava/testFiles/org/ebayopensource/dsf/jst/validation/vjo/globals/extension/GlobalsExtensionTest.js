vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.globals.extension.GlobalsExtensionTest")
//>needs(org.ebayopensource.dsf.jst.validation.vjo.globals.extension.Globals)
.globals({
	ext : 'extension' //<String
}, glob)
.props({
	main: function(){
		glob.ext.indexOf('ext');
		glob.ext.noSuchMethod('ext');
	}
})
.endType();