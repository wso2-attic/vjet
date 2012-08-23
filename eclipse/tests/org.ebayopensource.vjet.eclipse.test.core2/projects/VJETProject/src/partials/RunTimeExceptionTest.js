vjo.ctype('partials.RunTimeExceptionTest')
.props({
    s_init:false,

    /**
    * @return boolean
    * @access public
    */
    helloWorld:function(){
       alert("Hello VjO");
       return true;
   }

})
.protos({
  setName:function(name){
      var m_name = name;
  },

  getName

})
.inits(function() {
  vjo.samples.classes.HelloWorld.s_init = true;
}).endTypes();
