vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.inherits.Child")
.needs("org.ebayopensource.dsf.jst.validation.vjo.inherits.parent")
.inherits("org.ebayopensource.dsf.jst.validation.vjo.inherits.parent")
.protos({
	
	//> public constructs()
	constructs: function(){
		this.base();
	},

	//> public void doIt()
	doIt : function () {
		
		var c = new this(); //<Child
		//var p = new this.base(); //this is an invalid use case
		var p2 = this.base.privateProperty; //<String
		
	    alert(c.numberProperty);
	    
	    this.base.doIt();
	    this.base.sayIt();
	}
})
.props({
	//> public void main(String... )
	main: function(){
		
		alert("doing nothing for now");
	}
})
.endType();