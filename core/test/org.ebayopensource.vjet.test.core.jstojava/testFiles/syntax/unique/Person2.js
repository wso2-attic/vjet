vjo.ctype('syntax.unique.Person2')
.protos({
	m_defaultDept:undefined, //< public String
	
	m_defaultDept:undefined, //< public String
	
	m_publicAddress1:undefined, //< public String
	
	address:undefined, //< public String

	//> public void setName(String name)
	setName: function(name) {
		//Do Nothing
	},
	 
	//> public void setSex(String sex, String id)
	setSex: function(sex, id){
		this.m_publicSex = sex;  
	},
	
	//> public void setAddress1(String address, String id)
	setAddress1: function(address, id){
		this.m_publicAddress1 = address;
		this.address = address;
	},
	
	//> private void modAddress1(String address, String name)
	modAddress1: function(address, name){
		this.m_publicAddress1 = address + "!";
	}
	
})
.endType();