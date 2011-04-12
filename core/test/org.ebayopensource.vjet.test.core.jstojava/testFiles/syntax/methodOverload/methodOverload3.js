vjo.ctype('syntax.methodOverload.methodOverload3')
.props({

	//> public void setProject(int project1)
	//> public void setProject(String project1)
	//> public void setProject(String project1, String project2, int intNumber)
	//> public void setProject(String project1, String project2)
	setProject: function(project1, project2, project3) {
	// Implementation Here
	},
	
	//public void init1()
	init1 : function(){
		this.setProject(1);
		this.setProject("String");
		this.setProject("String","String1",30);
		this.setProject("String","string1");
	}
})
.endType();