vjo.ctype('syntax.global.vjoType.VjoType') //< public
.props({
 
	//> public void main(String... args)
	main: function(args) {
	var s = new Function(){
		vjo.sysout.println(vjo.getType("VjoType"));
	};//<Function
	s.call();
 	},
 	
	//>public void foo() 
	foo : function(){
		vjo.sysout.println(vjo.getType("syntax.global.vjoType.VjoType"));
		vjo.sysout.println(vjo.getType("VjoType"));
	},
	
	//>public void foo1() 
	foo1 : function(){
		var s = vjo.make(this, "AnonType");
	},
	
	//>public void foo2() 
	foo2 : function(){
		var array = vjo.createArray();
		var array1 = vjo.createArray(3);
		var array2 = vjo.createArray(3, this);
	},
	
	//>public void foo3() 
	foo3 : function(){
		var bo = vjo.isArray(this);
	},
	
	//>public void foo4() 
	foo4 : function(){
	var s = Function(){
		  
	};
		vjo.curry(s, new Object());
	},
	
	//>public void foo5() 
	foo5 : function(){
		vjo.syserr.print("D");
		vjo.syserr.printStackTrace();
		vjo.syserr.println("D");
		vjo.sysout.print("D");
		vjo.sysout.printStackTrace();
		vjo.sysout.println("D");
	}
})
.protos({

})
.endType();