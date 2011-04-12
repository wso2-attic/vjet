vjo.ctype('BugJsFiles.Bug2323') //<public 
.props({
	validStaticProp1 : 20, //< int
	validstaticProp2 : "String", //< String
	
	main : function(){ //< public void main(String...)
		this.
		var functionTest = new this.vj$.FunctionTest();//<FunctionTest
		var functionTestVar = functionTest.validProp2.big();
		this.staticFunc1("Test", 10);
		if(this.validStaticProp1){
			this.staticFunc2(new Array(1,2), new Date());
		} else {
			alert(this.staticFunc1("Test", 10));
		}
	},
	//> public String staticFunc1(String, int)
	//> public String staticFunc1(String)
	//> public String staticFunc1(int)
	//> public String staticFunc1()
	staticFunc1 : function(s1, n1){ 
		var arr = new Array();//<Array
		var i = 10;//<int
		for(i in arr){
			if (i > 5) {
				this.staticFunc2(new Array(1,2), new Date());
			} else {
				//Do nothing
			}
		}
		return this.validstaticProp2;
	},
	
	staticFunc2 : function(a1, d1){ //< private final void staticFunc2(Array, Date)
		var count = 0;//<int
		while(true){
			if(count > 5)
				break;
			count ++;
		}
	}
})
.protos({
	validProp1 : 30, //< int
	validProp2 : "Test", //< String
	
	constructs:function(){ //<public constructs()
		var date = new Date();//<Date
		date.toString();
	},
	
	instanceFunc1 : function(){ //<public void instanceFunc1() 
		var functionTest1 = new this.vj$.FunctionTest();//<FunctionTest
		var functionTestVar1 = functionTest1.validProp2.big();
		do {
			if(this.validProp1 > 30)
				break;
		} while (true);
	},
	
	instanceFunc2 : function(){ //<private final String instanceFunc2() 
		switch (this.validProp1) {
			case 30:
				this.instanceFunc1();
				this.vj$.FunctionTest.staticFunc1("Test", 10);
				break;
			default:
				break;
		}
		return "";
	}
})
.inits(function(){
	this.validStaticProp1 = 10;
	this.validStaticProp2 = this.staticFunc1("test", this.validStaticProp1);
})
.endType();