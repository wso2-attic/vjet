vjo.ctype('presenter.FunctionPositionTest') //<public 
.mixin('presenter.MyMixin')
.props({
	validStaticProp1 : 20, //< int
	validstaticProp2 : "String", //< String
	
	main : function(args){ //< public void main(String...)
		var functionTest = new this.vj$.FunctionTest();//<FunctionTest
		var functionTestVar = functionTest.<<45>>validProp2.big();
		<<0>>this.<<1>>staticFunc1(<<38>>"Test", 10);
		if(<<2>>this.<<3>>validStaticProp1){
			<<4>>this.<<5>>staticFunc2(new Array(1,2), <<40>>new Date());
		} else {
			<<6>>alert(<<7>>this.<<8>>staticFunc1(<<39>>"Test", 10));
		}<<9>>
	},
	//> public String staticFunc1(String, int)
	//> public String staticFunc1(String)
	//> public String staticFunc1(int)
	//> public String staticFunc1()
	staticFunc1 : function(s1, n1){ 
		var arr = <<10>>new Array();//<Array
		var i = 10;//<int
		for(i in <<11>>arr){
			if (<<12>>i > <<13>>5) {
				<<14>>this.<<15>>staticFunc2(new Array(1,2), new Date());
			} else {<<16>>
				//Do nothing
			}
		}<<17>>
		return <<18>>this.<<44>>validstaticProp2;
	},
	
	staticFunc2 : function(a1, d1){ //< private final void staticFunc2(Array, Date)
		var count = 0;//<int
		while(<<19>>true){
			if(<<20>>count > <<21>>5)
				<<22>>break;
			<<23>>count ++;
		}<<24>>
	}
})
.protos({
	validProp1 : 30, //< int
	validProp2 : "Test", //< String
	
	constructs:function(){ //<public constructs()
		<<25>>var date = <<26>>new Date();//<Date
		<<27>>date.toString();
	},
	
	instanceFunc1 : function(){ //<public void instanceFunc1() 
		var functionTest1 = new this.vj$.FunctionTest();//<FunctionTest
		var functionTestVar1 = functionTest1.<<46>>validProp2.big();
		do {<<28>>
			if(<<29>>this.<<30>>validProp1 > 30)
				<<31>>break;
		} while (<<32>>true);
	},
	
	instanceFunc2 : function(){ //<private final String instanceFunc2() 
		switch (<<33>>this.validProp1) {
			case <<34>>30:
				<<35>>this.instanceFunc1();
				this.<<41>>vj$.FunctionTest.<<42>>staticFunc1(<<43>>"Test", 10);
				break;
			default:
				<<36>>break;
		}
		return <<37>>"";
	},
	
	//>public void method1()
     method1 : function() { 
             <<55>>this.vj$.FunctionPosition.main(); 
    }	
})
.inits(function(){
	<<47>>this.<<48>>validStaticProp1 = 10;
	<<49>>this.<<50>>validStaticProp2 = <<51>>this.<<52>>staticFunc1("test", <<53>>this.<<54>>validStaticProp1);
})
.endType();