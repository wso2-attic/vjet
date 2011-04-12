vjo.ctype('syntax.methodOverload.methodOverload4')
.props({

	//> public void setProject(int project1)
	//> public void setProject(String project1)
	//> public void setProject(String project1, String project2, int project3)
	//> public void setProject(String project1, String project2)
	setProject: function(project1, project2, project3) {
	// Implementation Here
	},
	
	//public void init1()
	init1 : function(){
		this.setProject(1);
		this.setProject("String");
		this.setProject(382,"String1", 39);
		//this.setProject(23,32);
	}
})
.endType();