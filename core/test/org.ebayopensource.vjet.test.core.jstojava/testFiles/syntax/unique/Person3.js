vjo.ctype('syntax.unique.Person3')
.protos({
	
	m_publicAddress1:undefined, //< public String
	
	address:undefined, //< public String

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
.props({
	m_publicAddress1:undefined, //< public String
	
	address:undefined, //< public String
})
.endType();