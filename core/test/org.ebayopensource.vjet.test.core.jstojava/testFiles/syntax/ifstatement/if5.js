vjo.ctype('syntax.ifstatement.if5')
.props({
 
	//> public void main(String... args)
	main: function(args) {
		 vjo.sysout.println("DD"+arguments.length);
		 
		 var i1 = 30;
			
		 oo :  for( i1 = 0;i1 < 30; i1++){
		  	 if(i1 = 22){
		  		break oo; 	
		  	 }
		  }
	 }
})
.protos({

})
.endType();