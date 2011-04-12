vjo.ctype('constructorAdvisor.StaticPropAdvisorAType') //<public abstract
.props({
  saprop1 : 25,
  saprop2 : "Test",

  saFunction1 :  function () {
 	
  },
  
  main: function() {
			var x = new Array();
			alert("Hi");
	},

  saFunction2 :  function () {
	
  }
})
.protos({
  paprop2 : "Test",

  paFunction1 :  function () {
	
  },
  
  //> public void constructs 
	 constructs : function(){
 
 	} 
})
.endType();