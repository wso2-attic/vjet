vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.objcreation.Objcreation").props({
  m: function(){
    var test = new this.vj$.Objcreation("100");//<Objcreation
	test.getName();
  }
})
.protos({
  i_name:null, //< private String
  
  //> public void constructs(String p_name)
  constructs:function(p_name) {
	this.i_name = p_name;
  },
  
  //> public String getName()
  getName: function() {
    return this.i_name;
  }
 })
.inits(function(){})
.endType();