vjo.ctype('org.ebayopensource.vjo.runtime.tests.metatype.jstests.ConstructorsTest') //< public
//> needs org.ebayopensource.vjo.runtime.tests.metatype.testData.ConstructorsTestData
.needs('org.ebayopensource.vjo.runtime.tests.metatype.testData.ConstructorsTestData')
.needs('org.ebayopensource.vjo.runtime.tests.metatype.testData.XTypeA')
.needs('vjo.reflect.Modifier')
.needs('vjo.reflect.Constructor')
.props({
    //> public void main()
    main:function(){
    
    	//get Class [create instance if needed]
    	var aObject=new this.vj$.ConstructorsTestData("A-String",10);
    	var aClass = aObject.getClass();//<<
    	
    	// check if metadata is loaded [should not be loaded]
    	if(aClass._rtti != null){
    		throw "Metadata loaded , should be loaded lazily";
    	}
    	
    	// get constructors
    	var constructors = aClass.getConstructors(); //< vjo.reflect.Constructor[]
    	
        // use constructor 1
        {
        	
        	var constructor = constructors[0]; //< vjo.reflect.Constructor
        	// use - public int getModifiers()
            if(!this.vj$.Modifier.isPublic(constructor.getModifiers())){
            	throw "Modifer form Metadata info is wrong";
            }
        	
            // use - public vjo.Class[] getParameterTypes()
            var paramTypes = constructor.getParameterTypes();//< vjo.Class[]
            if(!paramTypes.length == 2){
            	throw "constructor param count form Metadata info is wrong";
            }
            

            if(!(paramTypes[0] === aClass)){
            	throw "Class Instance is not singleton , [param1:"+paramTypes[0]+"]&[param2:"+aClass+"]";
            }

            if(!(paramTypes[1] === org.ebayopensource.vjo.runtime.tests.metatype.testData.XTypeA.clazz)){
            	throw "Class Instance is not singleton , [param1:"+paramTypes[1]+"]&[param2:"+org.ebayopensource.vjo.runtime.tests.metatype.testData.XTypeA.clazz+"]";
            }
           
            
            // use -public vjo.Class getDeclaringClass()
            var declaringClass = constructor.getDeclaringClass();//< vjo.Class
            /**BUG:6657:START-KAMLESH**/
            if(!(aClass === declaringClass)){
            	throw "Class Instance is not singleton , [param1:"+aClass+"]&[param2:"+declaringClass+"]";
            }
            /**BUG:6657:END-KAMLESH**/
        }
        // use constructor 2
        {
        	
        	var constructor = constructors[1]; //< vjo.reflect.Constructor
        	// use - public int getModifiers()
            if(!this.vj$.Modifier.isPublic(constructor.getModifiers())){
            	throw "Modifer form Metadata info is wrong";
            }
        	
            // use - public vjo.Class[] getParameterTypes()
            var paramTypes = constructor.getParameterTypes();//< vjo.Class[]
            if(!paramTypes.length == 2){
            	throw "constructor param count form Metadata info is wrong";
            }

            if(!(paramTypes[1] === org.ebayopensource.vjo.runtime.tests.metatype.testData.XTypeA.clazz)){
            	throw "Class Instance is not singleton , [param1:"+paramTypes[1]+"]&[param2:"+org.ebayopensource.vjo.runtime.tests.metatype.testData.XTypeA.clazz+"]";
            }
           
            
            // use -public vjo.Class getDeclaringClass()
            var declaringClass = constructor.getDeclaringClass();//< vjo.Class
			/**BUG:6657:START-KAMLESH**/
            if(!(aClass === declaringClass)){
            	throw "Class Instance is not singleton , [param1:"+aClass+"]&[param2:"+declaringClass+"]";
            }
            /**BUG:6657:END-KAMLESH**/
        }

        // compare declaring classses from constructors
        if(!((constructors[0].getDeclaringClass() === constructors[1].getDeclaringClass())&&(constructors[1].getDeclaringClass() === aClass ))){
        	throw "Class Instance is not singleton ,"+ 
        	"[param1:"+constructors[0].getDeclaringClass()+
        	"]&[param2:"+constructors[1].getDeclaringClass()+
        	"]&[param3:"+aClass;
        }
        // check if metadata is loaded [should be loaded]
    	
        if(aClass._rtti === null){
    		throw "Metadata is not loaded , should be loaded lazily";
    	}
    	
        //get class by other means and compare instances
        if(!((aClass === this.vj$.ConstructorsTestData.clazz)&&(aClass === org.ebayopensource.vjo.runtime.tests.metatype.testData.ConstructorsTestData.clazz))){
        	throw "Class Instance is not singleton , [param1:"+aClass+
        	"],[param2:"+this.vj$.ConstructorsTestData.clazz+
        	"],[param3:"+org.ebayopensource.vjo.runtime.tests.metatype.testData.ConstructorsTestData.clazz+
        	"]";
        }

    }
    
})
.endType();