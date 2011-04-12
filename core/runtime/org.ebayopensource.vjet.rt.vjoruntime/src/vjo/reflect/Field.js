vjo.ctype('vjo.reflect.Field')
.needs('vjo.reflect.Modifier')
.protos({
	m_class : undefined, //< private vjo.Class
	m_name : undefined, //< private String 
	m_type : undefined, //< private vjo.Class 
	m_modifiers : 0x0000, //< private int

	//> public constructs(vjo.Class declaringClass, String name, vjo.Class type, int modifiers)
	constructs : function (declaringClass, 
		name, 
		type, 
		modifiers) {
		
		this.m_class = declaringClass;
		this.m_name = name;
		this.m_type = type;
		this.m_modifiers = modifiers;
		
	},
	
	//>public vjo.Class getDeclaringClass()
	getDeclaringClass : function () {
		return this.m_class;
	},
	
	//>public String getName() 
	getName : function () {
		return this.m_name;
	},
	
    //> public int getModifiers()
    getModifiers : function ()
	{
		return this.m_modifiers;
    },
	
	//> public vjo.Class getType()
	getType : function (){
		return this.m_type;
	}

})
.endType();