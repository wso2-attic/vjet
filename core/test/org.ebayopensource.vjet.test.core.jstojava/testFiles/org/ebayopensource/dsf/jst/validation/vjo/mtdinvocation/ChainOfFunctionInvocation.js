vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.mtdinvocation.ChainOfFunctionInvocation')
.props({
	foo: function(){ //< (int function(String)) foo ()
		return null;
	},
	
	bar: function(){ //< (int function(String)) bar (Date)
		return null;
	},
	
	main: function(){
		var i = this.foo()("hello");//<int
		
		var x = this.foo()(1); //<String
		
		//@see Js13ScriptTests.js#141
		new Function("a", "return true;")();
		
		var flex;
		
		flex()();
		
		var bR = this.bar(new Date());//<<
		var i2 = bR("1");//<int
		var i3 = this.bar(new Date())("1");//<int
	}
}).endType();