vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.BugGlobalVarUndefinedCheck")
.globals({
	UNKNOWN_GLOBAL:null
})
.props({

	main: function(){
		//acessing global variable with if check
		if(UNKNOWN_GLOBAL){
			alert(UNKNOWN_GLOBAL + UNKNOWN_GLOBAL());
		}
		
		//accessing global variable with conditional expr
		UNKNOWN_GLOBAL?alert(UNKNOWN_GLOBAL + UNKNOWN_GLOBAL()):alert('null');
		
		//accessing global variable with typeof check in if
		if(typeof(UNKNOWN_GLOBAL) != 'undefined'){
			alert(UNKNOWN_GLOBAL + UNKNOWN_GLOBAL());
		}
		
		//accessing global variable with typeof check in conditional expr
		UNKNOWN_GLOBAL?alert(UNKNOWN_GLOBAL + UNKNOWN_GLOBAL()):alert('null');
		typeof(UNKNOWN_GLOBAL) != 'undefined'?alert(UNKNOWN_GLOBAL + UNKNOWN_GLOBAL()):alert('null');
	}
}).endType();