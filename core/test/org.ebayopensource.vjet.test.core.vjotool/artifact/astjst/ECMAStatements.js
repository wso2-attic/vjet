vjo.ctype('astjst.ECMAStatements') //> public
.props({

	//> public void testAssign() 
	testAssign : function() {
		var zebra = "hello"; //< String
		
		zebra = "bye";
	},

	//> public void testIf()	
	testIf : function() {
		var flag = true; //< boolean
		
		if (flag) {
			
		}
	},
	
	//> public void testIfElse()
	testIfElse : function() {
		var flag = true; //< boolean
		
		if (flag) {
		
		} else {
		
		}
	},
	
	//> public void testDoWhile()
	testDoWhile : function() {
		var flag = false; //< boolean
		
		do {
		
		} while (flag);
	
	
	},
	
	//> public void testWhile()
	testWhile: function() {
		var flag = false; //< boolean
		
		while (flag) {
		
		}
	},
	
	//> public void testFor()
	testFor: function() {
		
		//> int
		for (var i = 0; i < 10; i++) {
		
		}
	
	},
	
	//test var outside  2, test only semicolons
	
	//> public void testSwitch()
	testSwitch: function() {
		var i = 42; //< Number
		
		switch (i) {
		case 17:
			break;
		default:
			break;
		}
	
	},
	
	//test switch with no default, 
	
	//> public void testTryCatch() 
	testTryCatch: function() {
		try {
		} catch (e) {
		}
	},
	
	//> public void testTryCatchFinally()
	testTryCatchFinally: function() {
		try {
		} catch (e) {
		} finally {
		}
	},
	
	//> public void testTryFinally() 
	testTryFinally: function() {
		try {
		} finally {
		}
	},
	
	//> public void testThrow()
	testThrow: function() {
		throw 'myException'
	},
	
	//> public int testReturn()
	testReturn: function() {
		return 42;
	},
	
	//> public void testLabel()
	testLabel: function() {
		myLabel: 
		while (true) {
			break myLabel;
		}

	}
})
.endType();