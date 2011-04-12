vjo.ctype('syntax.paramtypes.ArgTest1')
.needs('syntax.paramtypes.ArgTest2')
.protos({
	m_name:undefined, //< private String

	argTest2 : null, //< private ArgTest2

	//> private void setName(String name)
	setName: function(name) {
		this.m_name = name;
	},
	
	//> public void setArg2(ArgTest2 arg2)
	setArg2 : function(arg2){
		this.argTest2 = arg2;
	},
	
	//> private void setSex(boolean sex)
	setSex: function(sex){
	},
	
	//> int setAddress1(int address)
	setAddress1: function(address){
		return address;
	},
	
	//> public void setDomain(String, String)
	setDomain : function(country, city){
	},
	
	//> public void setDomain1(int, String)
	setDomain1 : function(port, city){
	},
	
	//> public void init2()
	init2 : function(){
		this.setDomain("HA", 20);
	},
		
	//> public void init1()
	init1 : function(){
		this.setDomain("HA", "JS");
	},
	
	//> public void init3()
	init3 : function(){
		this.setDomain1(true, "JS");
	},
	
	//> public void init4()
	init4 : function(){
		this.setDomain1("HA", "JS");
	}
	
})
.endType();