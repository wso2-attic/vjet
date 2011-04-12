vjo.ctype('syntax.paramtypes.Employee4')
.protos({
	m_name:undefined, //< private String
	
	//> private void setName(String name)
	setName: function(name) {this.m_name = name;
	},
	
	//> private void setSex(boolean sex)
	setSex: function(sex){
	},
	
	//> int setAddress1(String address)
	setAddress1: function(address){
		return address;
	},
	
	//> public void filldata()
	filldata: function(){
		this.setSex("HA");
		this.setSex(true);
		this.setName("HA");
	},
	
	//> public void filldata1()
	filldata1: function(){
		this.setSex("HA");
	}
	
})
.endType();