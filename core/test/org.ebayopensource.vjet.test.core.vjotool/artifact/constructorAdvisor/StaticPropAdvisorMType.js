vjo.mtype('constructorAdvisor.StaticPropAdvisorMType') 
.props({
  smprop1 : 25,
  smprop2 : "Test",
  
  smFunction1 :  function () {
   	
  },
  
  main: function() {
			var x = new Array();
			alert("Hi");
  },

  smFunction2 :  function () {
	
  }
})
.protos({
  pmprop2 : "Test",

  pmFunction1 :  function () {
 	
  },
  
  //> public void constructs 
	 constructs : function(){
 
 	} 
})
.endType();