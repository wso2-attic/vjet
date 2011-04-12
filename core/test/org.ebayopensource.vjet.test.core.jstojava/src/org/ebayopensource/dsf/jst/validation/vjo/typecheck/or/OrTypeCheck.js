vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.typecheck.or.OrTypeCheck")
.props({
	main: function(){
		var i = 1;//<int
		var s = new Date();//<Date
		
		//this assignment should force or expression type to be int
		var iAmInt = i || s;//<int
		
		//this assignment should force or expression type to be Date
		var iAmDate = i || s;//<Date
		
		//this assignment should cause an error and none candidate types satisfies
		var iAmError = i || s;//<Error
		
		var f = function(e){//<Error function(Error)
			return i || s;//this return statement should cause an error as none candidate types satisfies the signature
		};
		
		f(i || s);//this method call should cause an error as none candidates satisfied the method parameter type
	}
}).endType();