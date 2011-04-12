vjo.ctype('syntax.global.vjoType.VjoAssert') //< public
.props({
})
.protos({
		//>public void foreach() 
	foreach : function(){
		 vjo.assert(true,false);
		 vjo.assert("Dce","");
		 vjo.assert(10,20);
	}
})
.endType();