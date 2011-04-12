vjo.ctype('syntax.constructOverride.Employee')
.inherits('syntax.constructOverride.Person')
.protos({
	m_name:undefined, //< private String

	//> public void constructs(String dept)
	constructs:function(dept) {
		this.base('Test_Name');
	},

	//> public void setName(String name)
	setName: function(name) {
		this.base.setName(name);
		this.m_name = name;
	},
	
	//> void modAddress1(String address)
	modAddress1: function(address){
		address = address + "!";
	},
	
	//> private void mod1Address1(String address)
	mod1Address1: function(address){
		address = address + "!";
	}
})
.endType();