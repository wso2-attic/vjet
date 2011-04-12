vjo.ctype('syntax.unique.Person8')
.protos({
	m_publicAddress1:undefined, //< public String
	
	address:undefined, //< public String

	//> public void setAddress1(String address, String address)
	setAddress1: function(address, address){
		this.m_publicAddress1 = address;
		this.address = address;
	},
	
	//> public void setAddress2(String address1, String address)
	setAddress2: function(address1, address){
		this.m_publicAddress1 = address;
		this.address = address;
	},
	
	//> public void setAddress3(String address, String address)
	setAddress3: function(address1, address){
		this.m_publicAddress1 = address;
		this.address = address;
	}
	
})
.props({
})
.endType();