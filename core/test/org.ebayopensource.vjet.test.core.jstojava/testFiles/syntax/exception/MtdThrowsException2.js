vjo.ctype('syntax.exception.MtdThrowsException2')
.props({
	
})
.protos({
	//>public String foo(String) 
	foo : function(s){
		if(s == "V"){
			throw new vjo.Object();             
		}else{
			throw "";
		}  
		var v3 ;//<String
		throw this.foo1();
		throw s;
		throw 1;
		throw v3;
		throw "vv";
		throw null ;
	},
	
	//>public void foo1() 
	foo1 : function(){
		
	}
})
.endType();