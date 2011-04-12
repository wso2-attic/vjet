vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.rt.ctype.CTypeAlias")
.needs("org.ebayopensource.dsf.jst.validation.vjo.rt.ctype.UtilCType","UC")
.props({
	main:function(){
		var ucObj = new this.vj$.UC();//<UC
		ucObj.callUtil();
		
		this.vj$.UC.callUtilStatic();
		return -1;
	}
})
.endType();