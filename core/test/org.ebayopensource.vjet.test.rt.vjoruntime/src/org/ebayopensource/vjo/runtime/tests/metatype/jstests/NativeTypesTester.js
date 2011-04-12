vjo.ctype('org.ebayopensource.vjo.runtime.tests.metatype.jstests.NativeTypesTester') //< public
.needs('org.ebayopensource.vjo.runtime.tests.metatype.testData.IntTestsData')
.needs('vjo.reflect.Field')
.needs('vjo.reflect.Method')
.props({

	testNativeType : function(classObject , typeBeingTested){

		// check if metadata is loaded [should not be loaded]
    	if(classObject._rtti != null){
    		throw "Metadata loaded , should  be loaded lazily";
    	}
    	// fetch declared Fileds
		var filedsArr = classObject.getDeclaredFields();//< vjo.reflect.Field[]
		// fetch declared Fileds
		var methodsArr = classObject.getDeclaredMethods();//< vjo.reflect.Method[]
		// check if metadata is loaded [should be loaded]
    	if(classObject._rtti == null){
    		throw "Metadata not loaded , should be loaded lazily";
    	}

		{
			var fieldClass01 = filedsArr[0].getType();//< vjo.Class
			var fieldClass02 = filedsArr[1].getType();//< vjo.Class
			var fieldClass03 = filedsArr[2].getType();//< vjo.Class
			var fieldClass04 = filedsArr[3].getType();//< vjo.Class
			
			var methodClass01 = methodsArr[0].getReturnType();//< vjo.Class
			var methodClass02 = methodsArr[1].getReturnType();//< vjo.Class
			var methodClass03 = methodsArr[2].getReturnType();//< vjo.Class
			var methodClass04 = methodsArr[3].getReturnType();//< vjo.Class
			
			var methodParamClass01 = methodsArr[0].getParameterTypes()[0];//< vjo.Class
			var methodParamClass02 = methodsArr[1].getParameterTypes()[0];//< vjo.Class
			var methodParamClass03 = methodsArr[2].getParameterTypes()[0];//< vjo.Class
			var methodParamClass04 = methodsArr[3].getParameterTypes()[0];//< vjo.Class
			
			
			if(!((methodParamClass01 === methodParamClass02 ) &&( methodParamClass02 === methodParamClass03 )&& (methodParamClass03=== methodParamClass04))){
				throw "Class Instance is not singleton for native type "+typeBeingTested+" , [param1:"+methodParamClass01+"]&[param2:"+methodParamClass02+"]&[param3:"+methodParamClass03+"]"+"]&[param4:"+methodParamClass04+"]";
			}
			if(!((methodClass01 === methodClass02 ) &&( methodClass02 === methodClass03 )&& (methodClass03=== methodClass04))){
				throw "Class Instance is not singleton for native type "+typeBeingTested+" , [param1:"+methodClass01+"]&[param2:"+methodClass02+"]&[param3:"+methodClass03+"]"+"]&[param4:"+methodClass04+"]";
			}
			
			if(!((fieldClass01 === fieldClass02 ) &&( fieldClass02 === fieldClass03 )&& (fieldClass03=== fieldClass04))){
				throw "Class Instance is not singleton for native type "+typeBeingTested+" , [param1:"+fieldClass01+"]&[param2:"+fieldClass02+"]&[param3:"+fieldClass03+"]"+"]&[param4:"+fieldClass04+"]";
			}

			if(!((fieldClass01 === methodClass01 ) &&( methodClass01 === methodParamClass01 ))){
				throw "Class Instance is not singleton for native type "+typeBeingTested+" , [param1:"+fieldClass01+"]&[param2:"+methodClass01+"]&[param2:"+methodParamClass01+"]";
			}
		}       

	}
})
.endType();


