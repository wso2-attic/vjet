vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.objcreation.Absobjcreation") //<public abstract
.props({
  m: function(){
    var test = new this.vj$.Absobjcreation("100");//<Absobjcreation
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
.endType();