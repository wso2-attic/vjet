vjo.ctype('org.ebayopensource.vjo.runtime.tests.section.globals.Globals')
.globals({
	G4 : 'hello',
	G5 : 10
	
})
.inits(function(){
	vjo.sysout.println(G4);
	assertEquals('hello',G4);
	vjo.sysout.println(G5);
})
.endType();
