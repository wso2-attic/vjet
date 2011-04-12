vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.inherits.parent")
.protos({
	
	numberProperty: 10, //<Number
	
	privateProperty: "", //<private String
	
	//> public constructs()
	constructs: function(){
		this.numberProperty++;
	},
	
	//>private void sayIt()
	sayIt: function(){
		
	},
	
	//>protected void doIt()
	doIt: function(){
		alert(this.privateProperty);
	},
	
	//>public void sayIt2()
	sayIt2: function(){
	}
})
.endType();