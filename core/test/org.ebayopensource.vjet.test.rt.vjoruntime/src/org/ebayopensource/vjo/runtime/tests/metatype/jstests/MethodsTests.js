vjo.ctype('org.ebayopensource.vjo.runtime.tests.metatype.jstests.MethodsTests') //< public
//> needs org.ebayopensource.vjo.runtime.tests.metatype.testData.MethodsTestsData
.needs('org.ebayopensource.vjo.runtime.tests.metatype.testData.MethodsTestsData')
//> needs org.ebayopensource.vjo.runtime.tests.metatype.testData.XTypeA
.needs('org.ebayopensource.vjo.runtime.tests.metatype.testData.XTypeA')
//> needs org.ebayopensource.vjo.runtime.tests.metatype.testData.XTypeB
.needs('org.ebayopensource.vjo.runtime.tests.metatype.testData.XTypeB')
//> needs vjo.reflect.Modifier
.needs('vjo.reflect.Modifier')
//> needs vjo.reflect.Constructor
.needs('vjo.reflect.Method')

.props({
	//>public void main() 
	main : function(){

		//get Class [create instance if needed]
		var methodDataClass = this.vj$.MethodsTestsData.clazz; //< vjo.Class

		// check if metadata is loaded [should not be loaded]
    	if(methodDataClass._rtti != null){
    		throw "Metadata loaded , should  be loaded lazily";
    	}

		// fetch declared methods
		var methodsArr = methodDataClass.getDeclaredMethods();//< vjo.reflect.Method[]
		
		// check if metadata is loaded [should be loaded]
    	if(methodDataClass._rtti == null){
    		throw "Metadata not loaded , should be loaded lazily";
    	}

		if(methodsArr.length != 6){
    		throw "There should be 6 declared methods in "+methodDataClass;
    	}

		var method01  = methodsArr[0];//< vjo.reflect.Method
		
		if(!(method01 instanceof vjo.reflect.Method)){
			throw "Class metadata should return instance if vjo.reflect.Method";
		}
		// test different features of the method object
		//use - public vjo.Class getDeclaringClass()
		var declaringClass = method01.getDeclaringClass();//< vjo.Class
		// check instance of declaring class  , if it is singleton
		
		/**BUG:6657:START-KAMLESH**/
		  if(!(methodDataClass === declaringClass)){
 		  	throw "Class Instance is not singleton , [param1:"+methodDataClass+"]&[param2:"+declaringClass+"]";
 		  }
 		/**BUG:6657:END-KAMLESH**/
 		
		//use - public String getName() 
		if("staticFoo" != method01.getName()){
        	throw "method name is wrong in metadata , Expected : staticFoo , Actual : "+method01.getName();
        }
		
		// use - public int getModifiers()
        if(!this.vj$.Modifier.isPublic(method01.getModifiers())){
        	throw "Modifer form Metadata info is wrong";
        }
		
		//use - public vjo.Class getType()
		
		var rtnType = method01.getReturnType();//< vjo.Class
		// check if return type's metadata is loaded [should not be loaded]
    	if(rtnType._rtti != null){
    		throw "Metadata loaded , should be loaded lazily";
    	}
    	var tempmethods = rtnType.getMethods();
    	if(rtnType._rtti == null){
    		throw "Metadata is not loaded , should be loaded lazily";
    	}
    	// check instance of return type and parameter type class , if it is singleton
    			
		/**BUG:6657:START-KAMLESH**/
    	if(!((rtnType === org.ebayopensource.vjo.runtime.tests.metatype.testData.XTypeA.clazz)&&(rtnType === this.vj$.XTypeA.clazz) )){
        	throw "Class Instance is not singleton , [param1:"+rtnType+"]&[param2:"+org.ebayopensource.vjo.runtime.tests.metatype.testData.XTypeA.clazz+"]&[param3"+this.vj$.XTypeA.clazz+"]";
        }
        /**BUG:6657:END-KAMLESH**/
        
		//use - public vjo.Class[] getParameterTypes()
		var paramTypes = method01.getParameterTypes();//< vjo.Class[]
		var paramType = paramTypes[2];//< vjo.Class
		// check if return type's metadata is loaded [should not be loaded]
    	if(paramType._rtti != null){
    		throw "Metadata loaded , should be loaded lazily";
    	}
    	var tempmethods = paramType.getMethods();
    	if(paramType._rtti == null){
    		throw "Metadata is not loaded , should be loaded lazily";
    	}
    	
    	/**BUG:6657:START-KAMLESH**/
    	if(!((paramType === org.ebayopensource.vjo.runtime.tests.metatype.testData.XTypeB.clazz)&&(paramType === this.vj$.XTypeB.clazz ))){
        	throw "Class Instance is not singleton , [param1:"+paramType+"]&[param2:"+org.ebayopensource.vjo.runtime.tests.metatype.testData.XTypeB.clazz+"]&[param3"+this.vj$.XTypeB.clazz+"]";
        }
        /**BUG:6657:END-KAMLESH**/
		
	}
})
.endType();


