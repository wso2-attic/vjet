vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.ex.ValidationEx2') //< public
.inherits("org.ebayopensource.dsf.jst.validation.vjo.ex.ValidationEx1")
.props({
	A: "MYA", //<public static final
	
	//>public void main() 
	main : function(){
		this.A = "test"; 
	}
})
.protos({
	//>public void c() 
	c1 : function(){
		this.base.b(); 
		this.b();
	},
	
	//>public void c2() 
	c2 : function(){
		this.base.c();
		this.c();
	}
	
})
.endType();