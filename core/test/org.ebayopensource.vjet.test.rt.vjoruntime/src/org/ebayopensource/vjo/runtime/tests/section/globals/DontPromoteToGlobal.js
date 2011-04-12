vjo.ctype('org.ebayopensource.vjo.runtime.tests.section.globals.DontPromoteToGlobal') //< public
.globals({
	arg:vjo.NODEF,
	arg2:vjo.NODEF, //< Foo::bar
	arg3:10 //< Foo::bar
})
.inits(function(){

	if(typeof(arg)!="undefined")
		throw "arg must be undefined and not promoted. typeof(arg2)=" + typeof(arg);

	if(typeof(arg2)!="undefined")
		throw "arg2 must be undefined and not promoted. typeof(arg2)=" + typeof(arg2);
	
	if(typeof(arg3)!="number")
		throw "arg3 must be defined and promoted to global scope. typeof(arg3)=" + typeof(arg3);
	
})
.endType();



