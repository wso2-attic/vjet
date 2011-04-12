vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.unsupported.Dynamicproperty')//<public
.protos({
	 //>public void foo() 
    foo : function(){
       var o = this.bar();
       alert(o.name);
    },
    
    //>public Object bar() 
    bar : function(){
      var o = new Object();
      o.name = "Raja";
      o.location = "SJC";
      return o;
    }
})
.endType();