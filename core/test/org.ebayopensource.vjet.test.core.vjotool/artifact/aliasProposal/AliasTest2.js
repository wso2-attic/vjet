vjo.ctype('aliasProposal.AliasTest2') //< public
.needs('staticPropAdvisor.StaticPropAdvisorTest','MyAlias1')
.props({
  saprop1 : 25,
  saprop2 : "Test"
})
.protos({
 	aprop1 : 25,
    aprop2 : "Test",
    
 	//> public void constructs 
	 constructs : function(){
 	 } 
})
.endType();