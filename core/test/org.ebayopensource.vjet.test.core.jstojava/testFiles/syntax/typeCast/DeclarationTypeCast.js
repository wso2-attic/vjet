vjo.ctype('syntax.typeCast.DeclarationTypeCast') //< public
.props({
  
})
.protos({
//>public void foo() 
foo : function(){
	var inte = this;//<DeclarationTypeCast
	var stre = true;//<<boolean 
	inte = stre;
	var intvv = stre;//<<DeclarationTypeCast
	var s = this;//<Boolean
}
})
.endType();