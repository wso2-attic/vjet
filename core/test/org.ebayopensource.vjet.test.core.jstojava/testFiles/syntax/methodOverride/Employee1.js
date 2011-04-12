vjo.ctype('syntax.methodOverride.Employee1')
.inherits('syntax.methodOverride.Person')
.protos({
	m_name:undefined, //< private String

	//> public void constructs(String dept)
	constructs:function(dept) {
		this.base(dept);
	},

	//> private void setName(String name)
	setName: function(name) {
		this.base.setName(name);
		this.m_name = name;
	},
	
	//> private void setSex(String sex)
	setSex: function(sex){
		sex = "Sex is"+sex;
	},
	
	//> public void setAddress1(String address)
	setAddress1: function(address){
		address = "address is"+address;
	}
})
.endType();