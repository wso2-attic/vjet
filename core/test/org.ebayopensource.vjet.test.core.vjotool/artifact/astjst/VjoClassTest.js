vjo.ctype('astjst.VjoClassTest') //< public
.props({
	//>public void main(String... args) 
	main : function(args){
		var obj = new this.vj$.VjoClassTest();
		var oClass = obj.getClass(); //< vjo.Class
		
		var constructs = oClass.getConstructors(); //< vjo.reflect.Constructor[]
		var fields = oClass.getFields(); //< vjo.reflect.Field[]
		var methods = oClass.getMethods(); //< vjo.reflect.Method[]
		var declFields = oClass.getDeclaredFields(); //< vjo.reflect.Field[]
		var declMethods = oClass.getDeclaredMethods(); //< vjo.reflect.Method[]
		var annotations = oClass.getAnnotations(); //< Object[]
		var interfaces = oClass.getInterfaces(); //< Object[]
		var vjotype = oClass.getVjoType(); //< vjo.reflect.Type
		var name = oClass.getName(); //< String
		var simpleName = oClass.getSimpleName(); //< String
		var pkgName = oClass.getPackageName(); //< String
		var isInterface = oClass.isInterface(); //< boolean
		var str = oClass.toString(); //< String
		var modifiers = oClass.getModifiers(); //< int
		
		var oClass1 = vjo.Class.create("Test","ctype"); //< vjo.Class
		var isInst = oClass.isInstance(oClass1); //< boolean
	}
})
.protos({
	
})
.endType();