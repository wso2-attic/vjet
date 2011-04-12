vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.rtnflow.RtnFlow') //< public
.props({
	
	//> public int main(String... arguments)
	main:function(){
	   if(true){
	   	return 1;
	   }
	   else if(true){
	   	if(false){
	   		return 2;
	   	}
	   	else{
	   		return 3;
	   	}
	   }
	   else{
	   	return 4;
	   }
	},
	
	//> public String f()
	f: function(){
		if(true){
			return "0";
		}
	},
	
	//> public String f2()
	f2: function(){
		if(true){
			return "1";
		}
		else if(true){
			return "2";
		}
	},
	
	//> public String f3()
	f3: function(){
		if(true){
			return "1";
		}
		
		if(false){
			return "2";
		}
		else{
			return "2";
		}
	}

})
.endType();

