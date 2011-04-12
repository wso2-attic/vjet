vjo.ctype('syntax.continuebreak.continuebreak1')
.props({
 
	//> public void main(String... args)
	main: function(args) {
		 vjo.sysout.println("DD"+arguments.length);
		 
		 var i1 = 30; //<Number
			
		 oo :  for(var i1 = 0;i1 < 30; i1++){
		  	 if(i1 == 20){
		  		continue oo;
		  	 }else if(i1 == 22){
		  		break oo; 	
		  	 }else{
		  		vjo.sysout.println("DD"+i1);
		  	 }
		  }
        
	 }

})
.protos({

})
.endType();