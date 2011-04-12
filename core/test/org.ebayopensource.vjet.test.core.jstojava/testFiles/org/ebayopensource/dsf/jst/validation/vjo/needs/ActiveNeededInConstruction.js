//this error test is more complicated as our references will reside in constructor calls
//or our referenced types will be meta types
vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.needs.ActiveNeededInConstruction")
//>needs(org.ebayopensource.dsf.jst.validation.vjo.needs.ActiveNeeded)
.props({
	main: function(){
		//this line should be an error
		new org.ebayopensource.dsf.jst.validation.vjo.needs.ActiveNeeded();
	}
})
.endType();