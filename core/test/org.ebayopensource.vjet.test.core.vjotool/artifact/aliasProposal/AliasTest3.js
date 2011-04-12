vjo.ctype('aliasProposal.AliasTest3') //< public
.needs('staticPropAdvisor.StaticPropAdvisorTest')
.needs('staticPropAdvisor.StaticPropAdvisorTest1')
.needs('nonStaticPropAdvisor.ProtosAdvisorTest')
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