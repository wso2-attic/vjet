vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.rt.etype.ETypeAssign")
.needs('org.ebayopensource.dsf.jst.validation.vjo.rt.etype.BaseEType')
.props({
	
	f: function(){
		var one = this.vj$.BaseEType.ONE;//<org.ebayopensource.dsf.jst.validation.vjo.rt.etype.BaseEType
		this.vj$.BaseEType.ONE = null;
		this.vj$.BaseEType.TWO.prop = 100;
	}
	
})
.endType();