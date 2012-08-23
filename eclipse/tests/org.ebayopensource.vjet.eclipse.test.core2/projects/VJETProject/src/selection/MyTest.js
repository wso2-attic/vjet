vjo.ctype('selection.MyTest') //< public
.needs('selection.Bar')
.props({
 
    //> public void main(String... arguments)
	main: function(arguments) { 
		var a = new this.vj$.MyTest(); //<MyTest
		a.values();
		vjo.syserr.print('a');
		a = new this.vj$.MyTest();
 	}, 
	
 	myProp: 234, //<int
 	myProp2: "hello world", 
 	myStaticFunc:function(){ 

        document.URL;
        window.alert('hi');
        var d = new Date() //< Date

 	},

 	//> public void abc() 
 	abc : function(){
 		
 	},
 	
 	//> public string bug3743()
 	bug3743: function() {
 		var s = "hi";
 		return s;
 	},
 	
 	//> public void bug6428a()
	bug6428a: function(){
        var arr = new Array(2,3,"hi");//<Array
        var i = 0;//<int
        for (i in arr){
                vjo.sysout.println(arr[i]);
        }
	},
	
 	//> public void bug6428b()
	bug6428b: function(){
        var arr = new Array(2,3,"hi");//<Array
        var i = 0;//<int
        for (i in arr){
                vjo.syserr.print(arr[i]);
        }
	}
})
.protos({
	
	//>public void myProtos1() 
	myProtos1 : function(){
		var i = 0; //< int
		this.foo('a');
		var c = vjo.sysout;
	},
	
	//>public String foo(String a)
	//>public String foo(int a)
	foo : function (a) {
		return a;
	},

	//> public void zoo(int x)
	zoo : function (x) {
		var ax = this.foo(x);
		this.vj$.MyTest.abc();
		ax = ax + this.vj$.MyTest.myProp;
		var c = this.foo(x).length;
	},
	
	//> private int bar()
	bar:function(){
		var outerType = new this.vj$.MyTest() ;   //< MyTest
		var innerType = new outerType.InstanceInnerType() ;  //<MyTest.InstanceInnerType
		innerType.innerFunc();
		return 1;
	},

	InstanceInnerType : vjo.ctype()    
	.protos({
		z: 2, //<int
		innerFunc : function() {            
			var date = new Date(); //< Date
			date.getDate();
			this.z++;
			vjo.sysout.println('InstanceInnerType function called');             
			this.vj$.outer.outerFunc();  // this.vj$.outer keyword, use to access outer type members        
		}
	})
	.endType(),

	outerFunc : function(){        
		vjo.sysout.println('OuterType function called');    
	},
	
	//> public MyTest bug3535()
	bug3535:function() {
	    return new this.vj$.MyTest();
	},
	
	//> public void bug3744(String s)
	bug3744: function(s) {
	}
})
.endType();