vjo.ctype('access.scope.protectedModifier.ProtectedPerson1')
.protos({
	m_defaultDept:undefined, //< protected String
	
	m_publicSex:undefined, //< protected String
	
	m_publicAddress1:undefined, //< protected String

	//>public void constructs()
	constructs:function(){
	},

	//> protected void setName(String name)
	setName: function(name) {
		//Do Nothing
	},
	
	//> protected void setSex(String sex, String id)
	setSex: function(sex, id){
		this.m_publicSex = sex;
	},
	
	//> protected void setAddress1(String address, String id)
	setAddress1: function(address, id){
		this.m_publicAddress1 = address;
	},
	
	//> protected void modAddress1(String address, String name)
	modAddress1: function(address, name){
		this.m_publicAddress1 = address + "!";
	}
	
})
.props({
	x : 10, //< protected int
	
	//> protected int getX()
	getX : function()
	{
		return this.x;
	}	
})
.endType();