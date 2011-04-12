vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.needs.NeedsTestError")
//>needs(org.ebayopensource.dsf.jst.validation.vjo.needs.NeedsGlobal)
//this is just for the dependency graph to find the type in typespace
.globals({
	NGE : org.ebayopensource.dsf.jst.validation.vjo.needs.ActiveNeeded //<<
}, NG)
.props({
	main: function(){
		//this line should be error
		this.vj$.ActiveNeeded.foo();
		
		//this line should be error
		org.ebayopensource.dsf.jst.validation.vjo.needs.ActiveNeeded.foo();
		new org.ebayopensource.dsf.jst.validation.vjo.needs.ActiveNeeded();
	}
})
.endType();