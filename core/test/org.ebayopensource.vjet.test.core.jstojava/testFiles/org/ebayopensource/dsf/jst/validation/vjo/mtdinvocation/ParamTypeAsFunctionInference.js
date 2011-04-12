vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.mtdinvocation.ParamTypeAsFunctionInference')
.props({
	
	
	//> public void bar((int function(int)))
	bar: function(f){
		
	},
	
	//> public void foo(int, (int function(Date))?, boolean?)
	foo: function(i, f, b){
		
	},
	
	//> public void main()
	main: function(){
		this.bar(function(x){
			var i = x;//<int
			return i;
		});
		
		this.bar(function(s){
			var i = s;//<String
			return i;
		});
		
		this.foo(100, function(x){
			var i = x;//<Date
			return i;
		});
		
		this.foo(100, function(s){
			var i = s;//<String
			return i;
		});
		
		this.bar(function(fff){//<String function(String)
			
		});
		
		this.foo(1, function(d){//<int function(Date)
			return d.getDay();
		});
	}
	
}).endType();