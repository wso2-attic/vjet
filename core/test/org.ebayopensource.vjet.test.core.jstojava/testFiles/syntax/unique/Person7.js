vjo.ctype('syntax.unique.Person7')
.protos({
	m_publicAddress1:undefined, //< public String
	
	address:undefined, //< public String

	//> public void setAddress1(String address, String id)
	setAddress1: function(address, id){
		this.m_publicAddress1 = address;
		this.address = address;
	},
	
	//> public void setAddress1(String address, String name)
	setAddress1: function(address, name){
		this.address = 39;
		this.m_publicAddress1 = address + "!";
	},
	
	//> public void setAddress2(String address, String id)
	setAddress1: function(address, id){
		this.m_publicAddress1 = address + "!";
	}
})
.props({
})
.endType();