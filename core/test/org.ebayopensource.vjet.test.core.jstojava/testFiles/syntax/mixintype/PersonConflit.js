vjo.mtype('syntax.mixintype.PersonConflit')
.props({
	
	age : 0, //public int
	
	age : 0, //public int

	//> public void doIt1()
	doIt1:function(){
	},
	
	//> public void doIt1()
	doIt1:function(){
	}
})
.protos({
	
	name : "HA", //public String
	
	name : "HA", //public String

   //> public void doIt2()
	doIt2:function(){
	},
	
	//> public void doIt2()
	doIt2:function(){
	},
	
	
	 //> public void main(String[] args)
	main : function(args)
	{
	vjo.sysout.println(this.name);
	vjo.sysout.println("AGE");
	vjo.sysout.println('AGE');
	}
})
.endType();
