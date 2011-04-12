vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.mtdinvocation.ParamTypeAsFunction')
.props({
	foo: function(){ //< (int function(String)) foo ()
		return null;
	},
	
	//> public void bar((int function(String)))
	bar: function(f){
		var local = this.foo();//<<
		
		//good invocation and assignment
		var i = local("1");//<int
		
		//bad invocation and assignment
		var x = local(1);//<String
	},
	
	//> public void main((int function(String)))
	main: function(f){
		//good invocation and assignment
		var i = f("1");//<int
		
		//bad invocation and assignment
		var x = f(1);//<String
	}
	
}).endType();