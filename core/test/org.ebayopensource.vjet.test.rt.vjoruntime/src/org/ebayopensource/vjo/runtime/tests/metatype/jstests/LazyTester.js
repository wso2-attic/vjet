vjo.ctype('org.ebayopensource.vjo.runtime.tests.metatype.jstests.LazyTester') //< public
.needs('org.ebayopensource.vjo.runtime.tests.metatype.testData.XTypeA')
.props({

	compareWithXTypeA : function(classObject ){
		if(!((classObject === this.vj$.XTypeA.clazz) && (classObject === org.ebayopensource.vjo.runtime.tests.metatype.testData.XTypeA.clazz ) )){
        	throw "Class Instance is not singleton , [param1:"+classObject+"]&[param2:"+this.vj$.XTypeA.clazz+"]&[param2:"+org.ebayopensource.vjo.runtime.tests.metatype.testData.XTypeA.clazz+"]";
        }
	}
})
.endType();


