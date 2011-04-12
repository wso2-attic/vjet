vjo.ctype('syntax.continuebreak.continuebreak3')
.props({
 
	//> public void main(String... args)
	main: function(args) {
		 vjo.sysout.println("DD"+arguments.length);
		 
		 var i1 = 30; //<Number
		 var i2 = 20;//<Number
		 oo :  for(i1 = 0;i1 < 30; i1++){
			cc: for(i2 = 0;i2 <20; i2++){
				 if(i1 == 20){
				  		continue oo;
				  	 }else if(i1 == 22){
				  		break cc; 	
				  	 }else{
				  		vjo.sysout.println("DD"+i1);
				  	 }
			 }
		  }
        
	 }

})
.protos({

})
.endType();