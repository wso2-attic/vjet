vjo.ctype('syntax.paramtypes.Employee3')
.protos({
	m_name:undefined, //< private String

	//> private void setName(String name)
	setName: function(name) {
		this.m_name = name;
	},
	
	//> private void setSex(boolean sex)
	setSex: function(sex){
	},
	
	//> int setAddress1(int address)
	setAddress1: function(address){
		return address;
	},
	
	//> public void filldata()
	filldata: function(){
		this.setName(true);
		this.setSex("HA");
		this.setAddress1("DDDD");
	}
	
})
.endType();