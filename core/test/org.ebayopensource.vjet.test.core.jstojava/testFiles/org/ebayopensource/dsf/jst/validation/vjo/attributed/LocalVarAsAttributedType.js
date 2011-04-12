vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.attributed.LocalVarAsAttributedType")
.needs("org.ebayopensource.dsf.jst.validation.vjo.attributed.SimpleAttributor")
//> needs(org.ebayopensource.dsf.jst.validation.vjo.attributed.SilentAttributor)
//> needs(org.ebayopensource.dsf.jst.validation.vjo.attributed.List)
.props({
	//>public void main(String)
	main: function(psCssText){
		//hack to remove unused import error
		var attributorObj = new this.vj$.SimpleAttributor();//<SimpleAttributor
		
		//attributed type testing scenario 1 [active import]
			//instance attribute
	    	var intProtoLocal;//<SimpleAttributor:intProto
	    	var arrProtoLocal;//<SimpleAttributor:arrProto
	    	var funProtoLocal;//<SimpleAttributor:funProto
    		//instance attribute usage
	    	intProtoLocal = 1;
	    	arrProtoLocal = ["1", "2"];
	    	funProtoLocal();

    		//static attribute
	    	var intPropLocal;//<SimpleAttributor::intProp
	    	var arrPropLocal;//<SimpleAttributor::arrProp
	    	var funPropLocal;//<SimpleAttributor::funProp
	    	//static attribute usage    	
	    	intPropLocal = 1;
	    	arrPropLocal = ["1", "2"];
	    	funPropLocal();
    	
	    //attributed type testing scenario 2 [global]
	    	var doc;//<::document
	    	var al;//<::alert
	    	//global attribute usage
	    	doc.getElementById('none');
	    	al(doc.all);
	    
	    //attributed type testing scenario 3 [inactive import]
	    	var intProtoLocal2;//<SilentAttributor:intProto
	    	var arrProtoLocal2;//<SilentAttributor:arrProto
	    	var funProtoLocal2;//<SilentAttributor:funProto
    		//instance attribute usage
	    	intProtoLocal2 = 1;
	    	arrProtoLocal2 = ["1", "2"];
	    	funProtoLocal2();

    		//static attribute
	    	var intPropLocal2;//<SilentAttributor::intProp
	    	var arrPropLocal2;//<SilentAttributor::arrProp
	    	var funPropLocal2;//<SilentAttributor::funProp
	    	//static attribute usage    	
	    	intPropLocal2 = 1;
	    	arrPropLocal2 = ["1", "2"];
	    	funPropLocal2();
	    
	    //attributed type testing scenario 4 [array declaration using attributed type]
	    	var intProtoLocalArr;//<SimpleAttributor:intProto[]
	    	var arrProtoLocalArr;//<SimpleAttributor:arrProto[]
	    	var funProtoLocalArr;//<SimpleAttributor:funProto[]
    		//instance attribute usage
	    	intProtoLocalArr[0] = 1;
	    	arrProtoLocalArr[0] = ["1", "2"];
	    	funProtoLocalArr[0]();
	    	
	    	//static attribute
	    	var intPropLocalArr2;//<SimpleAttributor::intProp[]
	    	var arrPropLocalArr2;//<SimpleAttributor::arrProp[]
	    	var funPropLocalArr2;//<SimpleAttributor::funProp[]
	    	//static attribute usage    	
	    	intPropLocalArr2[0] = 1;
	    	arrPropLocalArr2[0] = ["1", "2"];
	    	funPropLocalArr2[0]();
	    
	    //attributed type testing scenario 5 [generics usage]
	    	var collectionOfIntProtoLocal;//<List<SimpleAttributor:intProto>
	    	var collectionOfArrProtoLocal;//<List<SimpleAttributor:arrProto>
	    	var collectionOfFunProtoLocal;//<List<SimpleAttributor:funProto>
	    	//instance attribute usage
	    	var getIntProto = collectionOfIntProtoLocal.get(0);//<int
	    	var getArrProto = collectionOfArrProtoLocal.get(0);//<String[]
	    	collectionOfFunProtoLocal.get(0)();
	    	
	    	//static attribute
	    	var collectionOfIntPropLocal;//<List<SimpleAttributor::intProp>
	    	var collectionOfArrPropLocal;//<List<SimpleAttributor::arrProp>
	    	var collectionOfFunPropLocal;//<List<SimpleAttributor::funProp>
	    	//static attribute usage
	    	var getIntProp = collectionOfIntPropLocal.get(0);//<int
	    	var getArrProp = collectionOfArrPropLocal.get(0);//<String[]
	    	collectionOfFunPropLocal.get(0)();
	    
	    //attributed type testing scenario 6 [local function usage]
	    	var funAttributedTypeLocal; //< SimpleAttributor:intProto function(SimpleAttributor:arrProto, SimpleAttributor:funProto)
	}
})
.endType();