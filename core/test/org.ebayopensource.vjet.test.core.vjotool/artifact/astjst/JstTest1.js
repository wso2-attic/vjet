vjo.ctype('astjst.JstTest1') //< public
.props({
	main : function(args){ //<public void main(String... args)
        var obj = new this();
    }
})
.protos({
	//>public void func1() 
	func1 : function(){
		var doc = document.URL;//<String
		doc = doc.toLocalString();
		Object obj = eval("Test");
	},
	
	//>public void func() 
	func : function(){
		vjo.sysout.print("Hi");
		vjo.sysout.println("Hi");
		vjo.syserr.print("Hi");
		vjo.syserr.println("Hi");
		var arr = new Array(2,3,"hi");//<Array
		var i = 0;//<int
		for (i in arr){
			vjo.sysout.print("Hi");
			vjo.sysout.println("Hi");
			vjo.syserr.print("Hi");
			vjo.syserr.println("Hi");
			var obj = new this();
		}
	}
}).endType();