vjo.ctype('access.scope.var.AccessVar1Tester')
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
		m_publicSex = sex;
	},
	
	//> void setAddress1(String address, String id)
	setAddress1: function(address, id){
		m_publicAddress1 = address;
	},
	
	//> void modAddress1(String address, String name)
	modAddress1: function(address, name){
		m_publicAddress1 = address + "!";
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