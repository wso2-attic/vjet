vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8822")
.protos({
	//>public void foo() 
	foo : function(){
	}
})
.props({
	
	s1:null,//<String[]
	s2	:null,//<String[]
	//>public void main(String... args) 
	main : function(args){
		this.s1 = args;//<<String[]
		this.s2 = args;
	}
})
.endType();

