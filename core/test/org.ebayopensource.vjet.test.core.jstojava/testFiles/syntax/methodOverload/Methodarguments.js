vjo.ctype('syntax.methodOverload.Methodarguments')
.props({

	//>public void func1(String, Date) 
	func1 : function(c, d){
		var x = a;	// undefined
		var y = b;	// undefined 
	},
	
	//>public void func2(String a, Date b) 
	func2 : function(c,d){     
		var x = a; // undefined
		var y = b; // undefined
	},
	
	//>public void func3() 
	func3 : function(a, b){     
		var x = a;	// defined type is Object
		var y = b;  // defined type is Object
	},
	
	//>public void func4(int a, String b) 
	func4 : function(a, d){    
		var x = a;	// defined type is int
		var y = b;  // Undefined
		var z = d; // defined type is String
	}
	
})
.endType();