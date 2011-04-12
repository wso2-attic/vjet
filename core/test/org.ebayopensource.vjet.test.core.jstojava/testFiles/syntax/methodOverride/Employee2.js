vjo.ctype('syntax.methodOverride.Employee2')
.inherits('syntax.methodOverride.Person1')
.protos({
	m_name:undefined, //< private String

	//> public void constructs(String dept)
	constructs:function(dept) {
		this.base(dept);
	},
	
	// Declared mehtod name is different
	//> public void setName1(String name)
	setName: function(name) {
		this.base.setName(name);
		this.m_name = name;
	},
	
	//> public void setSex(String sex)
	setSex: function(sex){
		sex = "Sex is"+sex;
	},
	
	// Declared mehtod name is different
	//> public void setAddress2(String address)
	setAddress1: function(address){
		address = "address is"+address;
	}
})
.endType();