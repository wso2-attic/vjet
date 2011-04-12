vjo.ctype('astjst.ECMANativeTests')
.protos({
	//>public void func1() 
	func1 : function(){
		var e = eval("1+2"); //<Number
		var pi = parseInt("10"); //<<
		
		var uri = encodeURI("astjst/ECMANativeTests"); //<String
		
		var obj = new Object(); //<Object
		
		var func = new Function(); //<Function
		
		var arr = new Array(6); //<Array
		
		arr.push(1);
		
		var obj1 = obj.constructor; //<Object
		
		var str = "ABC"; //<String
		var str1 = str.charAt(1); //<String
		
		var bool = new Boolean(true); //<Boolean
		var bool1 = bool.valueOf(); //<boolean
		
		var max = Number.MAX_VALUE; //<Number
		
		var err = new Error(); //<Error
		var errNum = err.number; //<double

	}
})
.props({
})
.endType();