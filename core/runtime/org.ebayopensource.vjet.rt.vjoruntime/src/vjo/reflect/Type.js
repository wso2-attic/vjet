vjo.ctype('vjo.reflect.Type')
.protos({
	m_name : undefined, //< String 
	m_modifiers : undefined, //< int

	//> public constructs(String name, int modifiers)
	constructs : function (name, 
		modifiers) {
		
		this.m_name = name;
		this.m_modifiers = modifiers;
	},

	//>public String getName() 
	getName : function () {
		return this.m_name;
	},
	
	//> public int getModifiers()
	getModifiers : function () {
		return this.m_modifiers;
	}

})
.endType();