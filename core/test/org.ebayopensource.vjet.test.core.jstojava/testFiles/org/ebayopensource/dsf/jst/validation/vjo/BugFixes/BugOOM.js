vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.BugOOM")
.props({
	Inner: vjo.ctype() //< public
		.protos({
			InnerNested: vjo.ctype() //< public
				.inherits("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.BugOOM")
				.protos({
					s: function(){
						var recur = this.s();
					}
				})
				.endType()
		})
		.endType()
})
.endType();