vjo.ctype('BugJsFiles.GenericCtype') //<public 
.needs('BugJsFiles.WeekDaysEType')
.props({
	validStaticProp1 : 20, //< int
	validstaticProp2 :undefined, //< GenericCtype
	RADIUS:5, //<double
	staticProp:"Str",//<String
	
	main : function() { //< public void main (String...)
		var ordinalVal = this.vj$.WeekDaysEType.SUN.ordinal(); //6245
		BugJsFiles.WeekDaysEType.MON();  //5029
		this.vj$.WeekDaysEType.MON();  //2673
		BugJsFiles.GenericCtype.staticFunc();//5029
		alert(typeof(this.validStaticProp1));
		var nodelistproposal = document.all; //< NodeList
        var nodeproposal = nodelistproposal.item(1); //< Node
        window.close();
    	document.close();
    	Math.random();
        nodeproposal.toString();
	},
	staticFunc : function() { //< private String staticFunc()
		return "";
	}
})
.protos({
	validProp1 : 30, //< int
	validProp2 : "Test", //< private String
	
	testFunc : function(){//<public String testFunc()
        var arr = new Array(1,2);//<Array
        var i = 0; //<int
        i = arr[1];
        switch (i) {    
        case 0:   
        	break;
        default:
            break;
        }
        return this.validProp2.length.toFixed();
    },
	
	/**
	 * Constructs the GenericCType jst type
	 */
	//>public constructs()
	constructs:function(){
		var date = new Date();//<Date
		date.toString(); //Bug5348
		this.validProp2.toString(); //Bug 5502
		this.vj$.GenericCtype.validstaticProp2.validStaticProp1; //Bug5502
		var win = window.defaultStatus;//Bug4747
		BugJsFiles.GenericCtype.staticFunc();//5029
	},
	
	 //> public double compute(String arg, int inte)
	 //> public double compute(String arg)
	 //> public double compute()
     compute:function(arg){
     	var localVar1, localVar2, localVar3;
     	vjo.sysout.println("Test");
     	vjo.sysout.print("Test");
     	vjo.syserr.println("Test");
     	vjo.syserr.print("Test");
        return this.vj$.GenericCtype.RADIUS;
     },
     
     //>public void func1(String str, Object obj, Date d) 
	func1 : function(string,object,date){
		
	}
     
     doIt : vjo.ctype()
	  .protos({
	      d:vjo.NEEDS_IMPL,
	      innerProp :"Hi",//<String
	      //>public void test() 
	      test : function(){
	    	  this.innerProp.big();
	      }
	  }).endType()
	
})
.inits( function() {
    var myInitBlockVaraible = "";//<String
    myInitBlockVaraible.big();
})
.endType();