vjo.ctype('syntax.methodOverload.methodOverload1')
.props({

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
	}
	
})
.endType();