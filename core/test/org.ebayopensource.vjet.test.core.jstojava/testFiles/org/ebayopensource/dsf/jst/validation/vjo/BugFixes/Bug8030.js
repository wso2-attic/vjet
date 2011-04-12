vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8030')
.props({
	inner: vjo.ctype().protos({
		foo: function(){
			var obj = new this.vj$.Bug8030();//<Bug8030
		}
	}).endType()
})
.endType();