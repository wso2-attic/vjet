vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug1170_1') //< public
.needs('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug1170_Enum')
.props({
	E1 : vjo.etype().values("HA, BA").endType(),
	E2 : vjo.etype().values("JA, BLAH").endType(),
	getDay : function(){
	        return this.vj$.Bug1170_Enum.SUNDAY;
	}
})
.endType();
