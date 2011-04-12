vjo.ctype('syntax.methodOverload.MethodOverloadOpt1')
.props({

	//>public void doIt(String a, String? b, int? c) 
	doIt : function(a,b,c){
		
	},
	
	//>public void main(String... args) 
	main : function(){
		this.doIt("test", 12); // not correct no validation error
		this.doIt("test", "test"); // correct 
		this.doIt("test", "test", 10); // correct 
//		this.doIt(true, true, "test"); // 
	}
	
})
.endType();