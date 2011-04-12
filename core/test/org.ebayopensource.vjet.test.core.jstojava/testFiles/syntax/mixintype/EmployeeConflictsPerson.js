vjo.ctype('syntax.mixintype.EmployeeConflictsPerson')
.mixin('syntax.mixintype.Person')
.props({
	
	age : 0, //<public int
	
	//> public void doIt1Mixin()
	doIt1:function(){
	}
})
.protos({

	name : "HA", //<public String
	
	//> public void doIt2Mixin()
	doIt2:function(){
	},

	 //> public void mainMixin(String[] args)
	mainMixin : function(args)
	{
		vjo.sysout.println(this.vj$.Person.age);
		vjo.sysout.println(this.name);
		vjo.sysout.println("AGE");
		vjo.sysout.println('AGE');
		vjo.sysout.println(45);
	}
})
.endType();
