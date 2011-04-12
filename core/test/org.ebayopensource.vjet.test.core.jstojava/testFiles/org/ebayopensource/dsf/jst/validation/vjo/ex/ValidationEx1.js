vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.ex.ValidationEx1') //< public

.props({
	//>public void main(String... arguments) 
	main : function(){
	
		this.doIt(arguments[0]);
		this.doIt(new Date(85,2,11,9,25,0,0));
		this.doIt(true);
	
		this.doIt("Hello");
	},
	
	//>public void doIt(String a) 
	doIt : function(a){		 
		var x = 'Hello'; //<String
		var y = 'Test'; //<String
		var z = x + y;
	},
	
	//>public String test2() 
	test2 : function(){
		return "";
	}
})
.protos({
	//>public void a() 
	a : function(){
	  var i =1;
	  if (i ==1 ) {
	  	var s = ""; //<String
	  	s.equals("");
	  }
	},
	
	//>private void b() 
	b : function(){
	 
	},
	
	//>protected void c() 
	c : function(){
	 this.b();
	},

	//>public constructs()
	constructs:function(){
	}

})
.endType();