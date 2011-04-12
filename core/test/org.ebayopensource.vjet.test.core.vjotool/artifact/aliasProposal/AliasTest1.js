vjo.ctype('aliasProposal.AliasTest1') //< public
.needs('staticPropAdvisor.StaticPropAdvisorTest','MyAlias1')
.needs('staticPropAdvisor.StaticPropAdvisorTest1')
.needs('nonStaticPropAdvisor.ProtosAdvisorTest')
.props({
  saprop1 : 25,
  saprop2 : "Test"
})
.protos({
	aprop1 : 25,
    aprop2 : "Test",
    
 	//> public constructs ()
	 constructs : function(){
 		var x= new this.vj$.MyAlias1(); //<MyAlias1
 		
 		
 	 } 
})
.endType();