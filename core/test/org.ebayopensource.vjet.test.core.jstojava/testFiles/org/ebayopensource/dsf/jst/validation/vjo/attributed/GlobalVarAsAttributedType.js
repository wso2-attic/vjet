vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.attributed.GlobalVarAsAttributedType")
.needs("org.ebayopensource.dsf.jst.validation.vjo.attributed.SimpleAttributor")
//> needs(org.ebayopensource.dsf.jst.validation.vjo.attributed.SilentAttributor)
//> needs(org.ebayopensource.dsf.jst.validation.vjo.attributed.List)
.globals({
	intProtoGlobal : null//<SimpleAttributor:intProto
	,
	arrProtoGlobal : null//<SimpleAttributor:arrProto
	,
	funProtoGlobal : null//<SimpleAttributor:funProto
	,
	intPropGlobal : null//<SimpleAttributor::intProp
	,
	arrPropGlobal : null//<SimpleAttributor::arrProp
	,
	funPropGlobal : null//<SimpleAttributor::funProp
	,
	doc : null//<::document
	,
	al : null//<::alert
	,
	intProtoGlobal2 : null//<SilentAttributor:intProto
	,
	arrProtoGlobal2 : null//<SilentAttributor:arrProto
	,
	funProtoGlobal2 : null//<SilentAttributor:funProto
	,
	intPropGlobal2 : null//<SilentAttributor::intProp
	,
	arrPropGlobal2 : null//<SilentAttributor::arrProp
	,
	funPropGlobal2 : null//<SilentAttributor::funProp
	,
	intProtoGlobalArr : null//<SimpleAttributor:intProto[]
	,
	arrProtoGlobalArr : null//<SimpleAttributor:arrProto[]
	,
	funProtoGlobalArr : null//<SimpleAttributor:funProto[]
	,
	intPropGlobalArr2 : null//<SimpleAttributor::intProp[]
	,
	arrPropGlobalArr2 : null//<SimpleAttributor::arrProp[]
	,
	funPropGlobalArr2 : null//<SimpleAttributor::funProp[]
	,
	collectionOfIntProtoGlobal : null//<List<SimpleAttributor:intProto>
	,
	collectionOfArrProtoGlobal : null//<List<SimpleAttributor:arrProto>
	,
	collectionOfFunProtoGlobal : null//<List<SimpleAttributor:funProto>
	,
	collectionOfIntPropGlobal : null//<List<SimpleAttributor::intProp>
	,
	collectionOfArrPropGlobal : null//<List<SimpleAttributor::arrProp>
	,
	collectionOfFunPropGlobal : null//<List<SimpleAttributor::funProp>
	,
	funAttributedTypeGlobal : null //< SimpleAttributor:intProto function(SimpleAttributor:arrProto, SimpleAttributor:funProto)
})
.props({
	//>public void main(String)
	main: function(psCssText){
		//hack to remove unused import error
		var attributorObj = new this.vj$.SimpleAttributor();//<SimpleAttributor
		
		//attributed type testing scenario 1 [active import]
    		//instance attribute usage
	    	intProtoGlobal = 1;
	    	arrProtoGlobal = ["1", "2"];
	    	funProtoGlobal();

	    	//static attribute usage    	
	    	intPropGlobal = 1;
	    	arrPropGlobal = ["1", "2"];
	    	funPropGlobal();
    	
	    //attributed type testing scenario 2 [global]
	    	//global attribute usage
	    	doc.getElementById('none');
	    	al(doc.all);
	    
	    //attributed type testing scenario 3 [inactive import]
    		//instance attribute usage
	    	intProtoGlobal2 = 1;
	    	arrProtoGlobal2 = ["1", "2"];
	    	funProtoGlobal2();

	    	//static attribute usage    	
	    	intPropGlobal2 = 1;
	    	arrPropGlobal2 = ["1", "2"];
	    	funPropGlobal2();
	    
	    //attributed type testing scenario 4 [array declaration using attributed type]
    		//instance attribute usage
	    	intProtoGlobalArr[0] = 1;
	    	arrProtoGlobalArr[0] = ["1", "2"];
	    	funProtoGlobalArr[0]();
	    	
	    	//static attribute usage    	
	    	intPropGlobalArr2[0] = 1;
	    	arrPropGlobalArr2[0] = ["1", "2"];
	    	funPropGlobalArr2[0]();
	    
	    //attributed type testing scenario 5 [generics usage]
	    	//instance attribute usage
	    	var getIntProto = collectionOfIntProtoGlobal.get(0);//<int
	    	var getArrProto = collectionOfArrProtoGlobal.get(0);//<String[]
	    	collectionOfFunProtoGlobal.get(0)();
	    	
	    	//static attribute usage
	    	var getIntProp = collectionOfIntPropGlobal.get(0);//<int
	    	var getArrProp = collectionOfArrPropGlobal.get(0);//<String[]
	    	collectionOfFunPropGlobal.get(0)();
	    
	}
})
.endType();