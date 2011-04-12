vjo.ctype('access.scope.var.AccessVar3Tester')
.protos({
	m_defaultDept:undefined, //< String
	
	m_publicSex:undefined, //< String
	
	m_publicAddress1:undefined, //< String

	//> void setName(String name)
	setName: function(name) {
		var childName = "String"; //< String
		vjo.sysout.println(childName);
	},
	
	//> void setSex(String sex, String id)
	setSex: function(sex, id){
	vjo.sysout.println(childName);
	vjo.sysout.println(this.childName);
	vjo.sysout.println(this.ha);
	vjo.sysout.println(ha);
	}
	
})
.props({
	x : 10, //< int
	
	//> int getX()
	getX : function()
	{
		return this.x;
	}	
})
.endType();