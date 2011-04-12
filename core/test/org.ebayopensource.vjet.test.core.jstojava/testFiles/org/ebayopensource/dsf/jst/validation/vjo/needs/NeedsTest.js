vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.needs.NeedsTest")
//>needs(org.ebayopensource.dsf.jst.validation.vjo.needs.NeedsGlobal)
//this is just for the dependency graph to find the type in typespace
.props({
	main: function(){
		NG.foo();
		new NG();
	}
})
.endType();