vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.attributed.LocalVarAsAttributedTypeError")
.needs("org.ebayopensource.dsf.jst.validation.vjo.attributed.SimpleAttributor")
//> needs(org.ebayopensource.dsf.jst.validation.vjo.attributed.SilentAttributor)
//> needs(org.ebayopensource.dsf.jst.validation.vjo.attributed.List)
.props({
	//>public void util(String)
	util: function(p){
		//do nothing, just used for parameter type matching in the invocations
	},
	
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
	    	intProtoLocal = "str"; //should result in an assignment failure
	    	arrProtoLocal = "str"; //should result in an assignment failure
	    	funProtoLocal("str"); //should result in an argument number mismatch failure
	    	this.util(intProtoLocal); //should result in an argument type mismatch failure
	    	this.util(arrProtoLocal); //should result in an argument type mismatch failure

    		//static attribute
	    	var intPropLocal;//<SimpleAttributor::intProp
	    	var arrPropLocal;//<SimpleAttributor::arrProp
	    	var funPropLocal;//<SimpleAttributor::funProp
	    	//static attribute usage    	
	    	intPropLocal = "str"; //should result in an assignment failure
	    	arrPropLocal = "str"; //should result in an assignment failure
	    	funPropLocal("str"); //should result in an argument number mismatch failure
	    	this.util(intPropLocal); //should result in an argument type mismatch failure
	    	this.util(arrPropLocal); //should result in an argument type mismatch failure

    	
	    //attributed type testing scenario 2 [active import, no match, invisible]
	    	var intProtoNotThere;//<SimpleAttributor:intProtoNotThere
	    	intProtoNotThere = 1;
	    	var intProtoInvisible;//<SimpleAttributor:intProtoInvisible
	    	intProtoInvisible = 1;
	    	var intProtoShouldUseProp;//<SimpleAttributor:intProp
	    	intProtoShouldUseProp = 1;
	    
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
	    	intProtoLocalArr[0] = "str"; //should result in an assignment failure
	    	arrProtoLocalArr[0] = "str"; //should result in an assignment failure
	    	funProtoLocalArr[0]("str"); //should result in an argument number mismatch failure
	    	this.util(intProtoLocalArr[0]); //should result in an argument type mismatch failure
	    	this.util(arrProtoLocalArr[0]); //should result in an argument type mismatch failure

	    //attributed type testing scenario 5 [generics usages]
	    	var listOfIntProtoNotThere;//<List<SimpleAttributor:intProtoNotThere>
	    	var getIntProtoNotThere = listOfIntProtoNotThere.get(0);//<int
	    	var listOfIntProtoInvisible;//<List<SimpleAttributor:intProtoInvisible>
	    	var getIntProtoInvisible = listOfIntProtoInvisible.get(0);//<int
	    	var listOfIntProtoShouldUseProp;//<List<SimpleAttributor:intProp>
	    	var getIntProtoShouldUseProp = listOfIntProtoShouldUseProp.get(0);//<int
	    
	    //attributed type testing scenario 6 [this.vj$]
	    	var funProtoLocalFromVj$ = null;//<this.vj$.SimpleAttributor:funProto
	    
	    //attributed type testing scenario 7 [instance attributes]
	    	var intProtoLocalFromInstance = null; //<attributorObj:intProto
	    	
	    //special global decl
		    gDoc = document;//<::document
	}
})
.endType();