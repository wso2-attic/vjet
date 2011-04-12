vjo.ctype('syntax.exception.MtdThrowsException3')
.props({
	
})
.protos({
	//>public String foo(String) 
	foo : function(s){
		if(s == "V"){
			throw null;
		}else{
			return "333";
		}  
	},
	
	//>public void foo1() 
	foo1 : function(){
		
	}
})
.endType();