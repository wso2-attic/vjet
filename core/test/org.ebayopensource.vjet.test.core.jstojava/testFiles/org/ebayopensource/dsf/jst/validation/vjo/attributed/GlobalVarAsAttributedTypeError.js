vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.attributed.GlobalVarAsAttributedTypeError")
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
	intProtoNotThere : null//<SimpleAttributor:intProtoNotThere
	,
	intProtoInvisible : null//<SimpleAttributor:intProtoInvisible
	,
	intProtoShouldUseProp: null//<SimpleAttributor:intProp
	,
	listOfIntProtoNotThere: null//<List<SimpleAttributor:intProtoNotThere>
	,
	listOfIntProtoInvisible: null//<List<SimpleAttributor:intProtoInvisible>
	,
	listOfIntProtoShouldUseProp: null//<List<SimpleAttributor:intProp>
	,
	funAttributedTypeGlobal : null //< SimpleAttributor:intProto function(SimpleAttributor:arrProto, SimpleAttributor:funProto)
	,
	funProtoGlobalFromVj$ : null //< this.vj$.SimpleAttributor:funProto
	,
	attributorGlobalObj : null //<SimpleAttributor
	,
	intProtoGlobalFromInstance : null //< attributorGlobalObj:intProto
})
.props({
	//>public void util(String)
	util: function(p){
	},
	//>public void main(String)
	main: function(psCssText){
		var attributorObj = new this.vj$.SimpleAttributor();//<SimpleAttributor
		//attributed type testing scenario 1 [active import]
			intProtoGlobal = "str"; //should result in an assignment failure
			arrProtoGlobal = "str"; //should result in an assignment failure
			funProtoGlobal("str"); //should result in an argument number mismatch failure
			this.util(intProtoGlobal); //should result in an argument type mismatch failure
			this.util(arrProtoGlobal); //should result in an argument type mismatch failure

			intPropGlobal = "str"; //should result in an assignment failure
	    	arrPropGlobal = "str"; //should result in an assignment failure
	    	funPropGlobal("str"); //should result in an argument number mismatch failure
	    	this.util(intPropGlobal); //should result in an argument type mismatch failure
	    	this.util(arrPropGlobal); //should result in an argument type mismatch failure
	    
    	//attributed type testing scenario 3 [inactive import]
	    	intProtoGlobal2 = 1;
	    	arrProtoGlobal2 = ["1", "2"];
	    	funProtoGlobal2();
	    	
	    	intPropGlobal2 = 1;
	    	arrPropGlobal2 = ["1", "2"];
	    	funPropGlobal2();
	    	
    	//attributed type testing scenario 4 [array declaration using attributed type]
	    	intProtoGlobalArr[0] = 1;
	    	arrProtoGlobalArr[0] = ["1", "2"];
	    	funProtoGlobalArr[0]();
	    	intProtoGlobalArr[0] = "str"; //should result in an assignment failure
	    	arrProtoGlobalArr[0] = "str"; //should result in an assignment failure
	    	funProtoGlobalArr[0]("str"); //should result in an argument number mismatch failure
	    	this.util(intProtoGlobalArr[0]); //should result in an argument type mismatch failure
	    	this.util(arrProtoGlobalArr[0]); //should result in an argument type mismatch failure
	    
	    //attributed type testing scenario 5 [generics usages]
	    	var getIntProtoNotThere = listOfIntProtoNotThere.get(0);//<int
	    	var getIntProtoInvisible = listOfIntProtoInvisible.get(0);//<int
	    	var getIntProtoShouldUseProp = listOfIntProtoShouldUseProp.get(0);//<int
	}
})
.endType();