vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.BugOverloadingArgTypeMismatch')
.protos({

	//> public boolean foo(String arg)
	//> public boolean foo(boolean arg)
	foo: function(arg){
		var b = null;//<boolean
		if(typeof(arg) == 'string' && (arg == 'true' || arg == 'false')){
			b = eval(arg);
		}
		else if(typeof arg == 'boolean'){
			b = arg;
		}
		
		return b?true:false;
	}
})
.endType();