vjo.ctype('org.ebayopensource.vjo.runtime.tests.metatype.jstests.IntTests') //< public
.needs('org.ebayopensource.vjo.runtime.tests.metatype.testData.IntTestsData')
.needs('org.ebayopensource.vjo.runtime.tests.metatype.jstests.NativeTypesTester') 
.needs('vjo.reflect.Field')
.needs('vjo.reflect.Method')
.props({
	//>public void main() 
	main : function(){
		this.vj$.NativeTypesTester.testNativeType(this.vj$.IntTestsData.clazz , "int");      
	}
})
.endType();


