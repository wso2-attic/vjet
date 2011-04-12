vjo.ctype('org.ebayopensource.vjo.runtime.tests.metatype.jstests.FieldsTests') //< public
.needs('org.ebayopensource.vjo.runtime.tests.metatype.testData.FieldsTestsData')
.needs('org.ebayopensource.vjo.runtime.tests.metatype.testData.XTypeA')
.needs('org.ebayopensource.vjo.runtime.tests.metatype.testData.XTypeB')
.needs('vjo.reflect.Modifier')
.needs('vjo.reflect.Field')
.props({
	//>public void main() 
	main : function(){

		//get Class [create instance if needed]
		var fieldTestsClass = this.vj$.FieldsTestsData.clazz; //< vjo.Class

		// check if metadata is loaded [should not be loaded]
    	if(fieldTestsClass._rtti != null){
    		throw "Metadata loaded , should  be loaded lazily";
    	}

		// fetch declared Fileds
		var filedsArr = fieldTestsClass.getDeclaredFields();//< vjo.reflect.Field[]
		
		// check if metadata is loaded [should be loaded]
    	if(fieldTestsClass._rtti == null){
    		throw "Metadata not loaded , should be loaded lazily";
    	}

		if(filedsArr.length != 4){
    		throw "There should be 6 declared fields in "+fieldTestsClass;
    	}
		{
			var fieldObject  = filedsArr[0];//< vjo.reflect.Field
			
			if(!(fieldObject instanceof vjo.reflect.Field)){
				throw "Class metadata should return instance if vjo.reflect.Field";
			}
			// test different features of the method object
			//use - public vjo.Class getDeclaringClass()
			var declaringClass = fieldObject.getDeclaringClass();//< vjo.Class
			// check instance of declaring class  , if it is singleton
			
			/**BUG:6657:START-KAMLESH**/
			  if(!(fieldTestsClass === declaringClass)){
	 		  	throw "Class Instance is not singleton , [param1:"+fieldTestsClass+"]&[param2:"+declaringClass+"]";
	 		  }
	 		/**BUG:6657:END-KAMLESH**/
	 		
			//use - public String getName() 
			if("staticFoo" != fieldObject.getName()){
	        	throw "method name is wrong in metadata , Expected : staticFoo , Actual : "+fieldObject.getName();
	        }
			
			// use - public int getModifiers()
	        if((!this.vj$.Modifier.isPublic(fieldObject.getModifiers())) || (!this.vj$.Modifier.isStatic(fieldObject.getModifiers()))){
	        	throw "Modifer form Metadata info is wrong";
	        }
			
			//use - public vjo.Class getType()
			
			var type = fieldObject.getType();//< vjo.Class
			// check if return type's metadata is loaded [should not be loaded]
	    	if(type._rtti != null){
	    		throw "Metadata loaded , should be loaded lazily";
	    	}
	    	var tempmethods = type.getMethods();
	    	if(type._rtti == null){
	    		throw "Metadata is not loaded , should be loaded lazily";
	    	}
	    	
	    	// check instance of return type and parameter type class , if it is singleton
	    			
			/**BUG:6657:START-KAMLESH**/
	    	if(!((type === org.ebayopensource.vjo.runtime.tests.metatype.testData.XTypeA.clazz)&&(type === this.vj$.XTypeA.clazz ))){
	        	throw "Class Instance is not singleton , [param1:"+type+"]&[param2:"+org.ebayopensource.vjo.runtime.tests.metatype.testData.XTypeA.clazz+"]&[param3"+this.vj$.XTypeA.clazz+"]";
	        }
	        /**BUG:6657:END-KAMLESH**/
		}
		{
			var fieldObject  = filedsArr[1];//< vjo.reflect.Field
			
			if(!(fieldObject instanceof vjo.reflect.Field)){
				throw "Class metadata should return instance if vjo.reflect.Field";
			}
			// test different features of the method object
			//use - public vjo.Class getDeclaringClass()
			var declaringClass = fieldObject.getDeclaringClass();//< vjo.Class
			// check instance of declaring class  , if it is singleton
			
			/**BUG:6657:START-KAMLESH**/
			  if(!(fieldTestsClass === declaringClass)){
	 		  	throw "Class Instance is not singleton , [param1:"+fieldTestsClass+"]&[param2:"+declaringClass+"]";
	 		  }
	 		/**BUG:6657:END-KAMLESH**/
	 		
			//use - public String getName() 
			if("staticBar" != fieldObject.getName()){
	        	throw "method name is wrong in metadata , Expected : staticBar , Actual : "+fieldObject.getName();
	        }
			
			// use - public int getModifiers()
	        if((!this.vj$.Modifier.isPrivate(fieldObject.getModifiers())) || (!this.vj$.Modifier.isStatic(fieldObject.getModifiers()))){
	        	throw "Modifer form Metadata info is wrong";
	        }
			
			//use - public vjo.Class getType()
			
			var type = fieldObject.getType();//< vjo.Class
			// check if return type's metadata is loaded [should not be loaded]
	    	if(type._rtti != null){
	    		throw "Metadata loaded , should be loaded lazily";
	    	}
	    	try{
	    		var tempmethods = type.getMethods();
	    		throw "Exception expected at above line: Metadata Not Available Exception";
	    	}catch(error){   		
	    		if(error.match("Metadata Not Available") == null){
	    			throw "Exception expected of type : Metadata Not Available Exception";
	    		}
	    	}
	    
	    	if(type._rtti != null){
	    		throw "Since metadata is not available , nothing should get loaded";
	    	}
	    		
		}
        

	}
})
.endType();


