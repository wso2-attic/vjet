vjo.ctype('access.scope.thiskeyword.ProtosThis1')
.protos({
	m_publicAddress1:undefined, //< private String

	//> public void setAddress1(String address, String id)
	setAddress1: function(address, id){
		this.m_publicAddress1 = address;
		this.modAddress1(address, id);
	},
	
	//> private void modAddress1(String address, String name)
	modAddress1: function(address, name){
		var s1 = "String"; //String
		vjo.sysout.println(s1); 
	}
})
.endType();