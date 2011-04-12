vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug6465")
.mixin("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug6465MType1")
.mixin("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug6465MType2")
.props({
	main: function(){
		this.vj$.Bug6465.foo();
		var bug6465 = new this.vj$.Bug6465();//<Bug6465
		bug6465.bar();
		
		this.vj$.Bug6465.doIt();
		bug6465.sayIt();
	}
})
.endType();