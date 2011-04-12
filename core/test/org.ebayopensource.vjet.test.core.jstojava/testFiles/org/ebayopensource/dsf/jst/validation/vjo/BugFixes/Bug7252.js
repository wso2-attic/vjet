vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug7252')
.protos({
	foo: function(){
		this.bar();
		
		var inner = vjo.make(this, this.vj$.Bug7252)
		.protos({
		})
		.endType();
		
		this.bar();
	},
	
	bar: function(){
	}
})
.endType();