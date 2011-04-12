vjo.ctype('BugJsFiles.Bug4073') //< public
.needs('staticPropAdvisor.StaticPropAdvisorTest','sAlias1')
.needs('staticPropAdvisor.StaticPropAdvisorTest1')
.needs('nonStaticPropAdvisor.ProtosAdvisorTest','sAlias2')
.props({
  saprop1 : 25,
  saprop2 : "Test"
})
.protos({
	aprop1 : 25,
    aprop2 : "Test",
    
 	//> public constructs 
	 constructs : function(){s
 	 } 
})
.endType();