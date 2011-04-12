vjo.ctype('syntax.paramtypes.Person')
.protos({
	m_defaultDept : int, //< private int
	
	m_publicSex : boolean, //< public boolean
	
	m_publicAddress1: String, //< public String

	//> public void constructs(int dept, String address, boolean sex)
	constructs:function(dept, address, sex) {
		this.m_defaultDept = dept;
		this.m_publicSex = sex;
		this.m_publicAddress1 = address;
	},

	//>	public String getAddress1()
	getAddress1: function(){
		return this.m_publicAddress1;
	},
	
	//> public boolean getSex()
	getSex : function(){
		return this.m_publicSex;
	},
	
	//> public int getName()
	getName : function(){
		return this.m_defaultDept;
	}
	
})

.endType();