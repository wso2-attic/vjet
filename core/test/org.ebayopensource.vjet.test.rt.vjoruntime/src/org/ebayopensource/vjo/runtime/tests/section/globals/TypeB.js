vjo.ctype('org.ebayopensource.vjo.runtime.tests.section.globals.TypeB') //< public
.inherits('org.ebayopensource.vjo.runtime.tests.section.globals.BaseTypeA')
.globals({
	G4 : 'hello', //< String
	G5 : undefined //< Date
})
.props({
  
})
.protos({
	
})
.inits(function(){
	G1 = false ;
	if (G4 === 'hello') {
		G4 = 'goodbye' ;
		G5 = new Date('03/17/1980') ;
	}
	else {
		fail("G4 should be hello");
		G4 = 'goodbye' ;
		G5 = new Date('06/22/1981') ;		
	}
})

.endType();