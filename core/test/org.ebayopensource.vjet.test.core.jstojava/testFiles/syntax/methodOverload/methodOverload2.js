vjo.ctype('syntax.methodOverload.methodOverload2')
.props({

	//> public void setProject()
	//> public void setProject(int project)
	//> public void setProject(String project)
	setProject: function(project) {
	// Implementation Here
	},
	
	//public void init1()
	init1 : function(){
		this.setProject(1);
	},
		
	//public void init2()
	init2 : function(){
		this.setProject("HA");
		this.setProject(true);
		this.setProject(23.09);
	}
})
.endType();