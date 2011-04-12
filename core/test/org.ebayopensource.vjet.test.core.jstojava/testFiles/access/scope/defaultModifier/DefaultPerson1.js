vjo.ctype('access.scope.defaultModifier.DefaultPerson1')
.protos({
	m_defaultDept:undefined, //< String
	
	m_publicSex:undefined, //< String
	
	m_publicAddress1:undefined, //< String

	//>public void constructs()
	constructs:function(){
	},

	//> void setName(String name)
	setName: function(name) {
		//Do Nothing
	},
	
	//> void setSex(String sex, String id)
	setSex: function(sex, id){
		this.m_publicSex = sex;
	},
	
	//> void setAddress1(String address, String id)
	setAddress1: function(address, id){
		this.m_publicAddress1 = address;
	},
	
	//> void modAddress1(String address, String name)
	modAddress1: function(address, name){
		this.m_publicAddress1 = address + "!";
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