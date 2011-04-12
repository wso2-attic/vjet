vjo.ctype('typecheck.compartible.TypecheckObjLiteral') //< public
//> needs(typecheck.compartible.TypecheckOtype)
.protos({
	
    main:function(){
		var normalObj = new Object();//<Object
		var objLiteral = {};//<ObjLiteral
		
		normalObj = objLiteral;//it's ok
		objLiteral = normalObj;//disallowed
		
		var anotherObjLiteral = {};//<ObjLiteral
		objLiteral = anotherObjLiteral;//it's ok
		anotherObjLiteral = objLiteral;//it's ok
		
		var ol = null;//<TypecheckOtype.ol
		ol = objLiteral;//assign failure
		objLiteral = ol;//it's ok
	}
})
.endType();