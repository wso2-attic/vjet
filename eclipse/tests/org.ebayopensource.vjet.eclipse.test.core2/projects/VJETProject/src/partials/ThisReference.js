vjo.ctype("partials.ThisReference")
.props({
	s_init:false,	
 	
    helloWorld:function(){
       alert("Hello VjO");
       return true;
   },
   
    helloWorld1:function(){
       alert("Hello VjO");
       return true;
   }

})
.protos({
	
    setName : function(name){
      this.vj$.ThisReference.
    },
   
   helloWorld2:function(){
       alert("Hello VjO");
       return true;
   },
   
    helloWorld3:function(){
       alert("Hello VjO");
       return true;
   }
	
}).endType();