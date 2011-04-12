vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug6452")
.needs("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.BugInnerType")
.props({
	main: function(){
		var outerType = new this.vj$.BugInnerType(); //< BugInnerType
		var innerStatic = new this.vj$.BugInnerType.StaticInnerType(); //< BugInnerType.StaticInnerType
		var innerType = new outerType.InstanceInnerType(); //< BugInnerType.InstanceInnerType
		
		outerType.foo();
		innerStatic.bla();
		innerType.bar();
	}
})
.endType();