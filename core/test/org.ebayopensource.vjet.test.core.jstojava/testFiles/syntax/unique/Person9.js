vjo.ctype('syntax.unique.Person9')
.protos({
	m_publicAddress1:undefined, //< public String
	
	address:undefined, //< public String

	//> public void setAddress1(String address, String id)
	setAddress1: function(address, id){
		this.m_publicAddress1 = address;
		this.address = address;
	}
	
})
.props({

	//> public void setAddress1(String address, String id)
	setAddress1: function(address, id){
	}
})
.endType();