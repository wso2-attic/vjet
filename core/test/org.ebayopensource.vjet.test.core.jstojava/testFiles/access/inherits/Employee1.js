vjo.ctype('access.inherits.Employee1')
.inherits('access.inherits.Person')
.protos({
	m_name:undefined, //< private String
	
	//> public void constructs(int val)
	constructs : function (val)
	{
		this.base(val*2, "Employee", true);
	},
	
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
		this.base.m_privateDept = 10;
	},
	
	//> public void filldata1(Person person)
	filldata1: function(person){
		vjo.sysout.println(this.base.getPrivateName());
	}
})
.endType();