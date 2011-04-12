vjo.ctype('syntax.paramtypes.Employee1')
.needs('syntax.paramtypes.Person')
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
		this.setName("HA");
		this.setSex(true);
		this.setAddress1(390);
	},
	
	//> public void filldata1(Person person)
	filldata1: function(person){
		this.setName(person.getName());
		this.setSex(person.getSex());
		this.setAddress1(person.getAddress1());
	},
	
	//> public void initMetaData()
	initMetaData : function(){
		this.filldata1(new this.vj$.Person(30, "HA", true));
	}
	
})
.endType();