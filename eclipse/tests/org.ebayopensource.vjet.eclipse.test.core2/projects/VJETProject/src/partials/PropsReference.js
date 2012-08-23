vjo.ctype("partials.PropsReference")
.props({
	s_init:false,	
 	
    main: function() { //< public void main (String ... arguments) 
 
 	},
 	
    helloWorld:function(){
       alert("Hello VjO");
       return true;
   }

})
.protos({
	
  setName : function(name){
    var m_name = name; 
 	 
  }
	
}).inits(function() {
  vjo.samples.classes.HelloWorld.s_init = true;
}).endType();