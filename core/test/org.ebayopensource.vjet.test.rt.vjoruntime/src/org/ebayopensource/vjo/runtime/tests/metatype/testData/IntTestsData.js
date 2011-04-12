vjo.ctype('org.ebayopensource.vjo.runtime.tests.metatype.testData.IntTestsData') //< public
.needs('vjo.reflect.Modifier')
.needs('vjo.TypeMetadata')
.props({

    staticFieldInt01 : null,//< public int
    staticFieldInt02 : null, //< public int
    
    //>public constructs()
    staticMethodInt01 : function(){},
    staticMethodInt02 : function(){}
    
})
.protos({

    instanceFieldInt01 : null ,
    instanceFieldInt02 : null ,
    
    instanceMethodInt01 : function(){},
    instanceMethodInt02 : function(){},
    
    //>public constructs()
    constructs : function(){
    	
    }  
    
})
.endType();

vjo.meta.load('org.ebayopensource.vjo.runtime.tests.metatype.testData.IntTestsData', 
function () {
	var data = 
	{
		type : ['org.ebayopensource.vjo.runtime.tests.metatype.testData.IntTestsData', vjo.reflect.Modifier.PUBLIC],
		
		fields : [
			['staticFieldInt01', "int" , vjo.reflect.Modifier.PUBLIC | vjo.reflect.Modifier.STATIC ],
			['staticFieldInt02',  'int', vjo.reflect.Modifier.PRIVATE | vjo.reflect.Modifier.STATIC ],
			['instanceFieldInt01', "int" , vjo.reflect.Modifier.PUBLIC | vjo.reflect.Modifier.STATIC ],
			['instanceFieldInt02', "int" , vjo.reflect.Modifier.PUBLIC | vjo.reflect.Modifier.STATIC ]
		],
		
		methods : [
			['staticMethodInt01',['int'], "int" , vjo.reflect.Modifier.PUBLIC | vjo.reflect.Modifier.STATIC ],
			['staticMethodInt02',['int'] , 'int', vjo.reflect.Modifier.PRIVATE | vjo.reflect.Modifier.STATIC ],
			['instanceMethodInt01',['int'], "int" , vjo.reflect.Modifier.PUBLIC | vjo.reflect.Modifier.STATIC ],
			['instanceMethodInt02',['int'] ,"int" , vjo.reflect.Modifier.PUBLIC | vjo.reflect.Modifier.STATIC ]
		],
		
		constructors : [
			[[], vjo.reflect.Modifier.PUBLIC]
		]
		};
		return data;
	}
);