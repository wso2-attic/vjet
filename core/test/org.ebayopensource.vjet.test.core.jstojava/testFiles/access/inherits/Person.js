vjo.ctype('access.inherits.Person')
.protos({
	m_privateDept : int, //< private int
	
	m_publicSex : boolean, //< public boolean
	
	m_defaultAddress1: String, //< String
	
	m_protectedAddress2: String, //< protected String

	//> public void constructs(int dept, String address, boolean sex)
	constructs:function(dept, address, sex) {
		this.m_privateDept = dept;
		this.m_publicSex = sex;
		this.m_defaultAddress1 = address;
	},

	//>	String getDefaultAddress1()
	getDefaultAddress1: function(){
		return this.m_defaultAddress1;
	},
	
	//>	protected String getProtectedAddress2()
	getProtectedAddress2: function(){
		return this.m_protectedAddress2;
	},
	
	//> public boolean getPublicSex()
	getPublicSex : function(){
		return this.m_publicSex;
	},
	
	//> private int getPrivateName()
	getPrivateName : function(){
		return this.m_privateDept;
	}
	
})

.endType();