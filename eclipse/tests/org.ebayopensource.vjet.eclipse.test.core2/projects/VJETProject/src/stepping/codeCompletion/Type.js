vjo.type("stepping.codeCompletion.Type")
.props({
	//> public int s_var1
	s_var1:0,

  	//> public int s_var2
	s_var2:0,

	stA : 15,
	
	innerFuncTwo : function(){
		window.alert("innerFunctiontwo");
		
	},

	//>public vjoCompletion.A stDoSomething()
	stDoSomething : function() {
		stA = 1;
	},
 	
	//> public void doStatic1(int z)
	doStatic1:function(z){
  		this.s_var1=z;
    },

	//> public void doStatic2()
	doStatic2:function(){this.doStatic1(30);
	},

	stFunc : function() {
		while(this.s_var1 < this.stA) {
			this.s_var1--;
		}
	},
	
	testField : 67,

	//>public void test()
	test : function() {
	   this.vj$.Type.testField;
 	   var z = this.vj$.Type;	
	   z.instanceOf(obj);
	   if(z typeof Object) {};  
	},

	//> public void main()
	main:function(){
		var testObj=new this();
		testObj.doIt(2,3,true);
	}
	
})
.protos({
	//> int m_var1
	m_var1:10,

	//>public void fooA()
	fooA : function() {
		vjo.$static(this).doSomething();
		vjo.$static(this).stA = 1;
	},

	//> public void func()
	func: function() {
		for(int i=m_var1; i>0; i++) {
			window.alert("Number is "+i);
		}
	},

	//> public int doIt(int x,int y,boolean isAdd)
	doIt:function(x,y,isAdd){
    	var sum=0;
        if(isAdd){
    	    sum=x+y;
    	}
  		return sum;
	},

	//> String state
    	state:null,
	
	//> protected void setState(String s)
	setState:function(s){
    	this.state=s;
	},

	//> protected String getState()
	getState:function(){
    	return this.state;
	},

	x : 0, //< int
	
	foo : function() {
		this.vj$.Type.stA = 1;
		this.x = 1;
		this.vj$.Type.stFunc();
	}
});
