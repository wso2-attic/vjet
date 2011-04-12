vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8698")
.props({
		//>public void main(String... args) 
	main : function(args){
		var out = vjo.sysout.println;//<<
        var str = "4";
        var mint = 10;
        this.foo(str+mint);
	},
	
	//>public void foo(String) 
	foo : function(s){
		
	}
})
.protos({
})
.inits(function(){
})
.endType();
