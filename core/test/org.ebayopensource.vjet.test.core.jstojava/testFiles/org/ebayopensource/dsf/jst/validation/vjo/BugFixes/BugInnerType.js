vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.BugInnerType")
.props({
	main: function(){
		var outerType = new this.vj$.BugInnerType(); //< BugInnerType
		var innerType = new outerType.InstanceInnerType(); //< BugInnerType.InstanceInnerType
		
		outerType.foo();
		innerType.bar();
	},
	
	StaticInnerType: vjo.ctype() //<public
		.protos({
			bla: function(){
				var outerType = new this.vj$.BugInnerType(); //< BugInnerType
				outerType.foo();
			}
		}).endType()
})
.protos({

	foo: function(){
		var innerType = new this.InstanceInnerType();//< BugInnerType.InstanceInnerType
		innerType.bar();
		//innerType.foo();
	},
	
	InstanceInnerType: vjo.ctype() //<public
		.protos({
			bar: function(){
			}
		}).endType()
})
.endType();