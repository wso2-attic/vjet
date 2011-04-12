vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8833")
.protos({

	//> public void overload(String, int)
	//> public void overload(String)
	overload: function(a,b){
	}
})
.props({

	main: function(){
		var obj = new this();//<Bug8833
		obj.overload('correct');//no error
		obj.overload('correct', 1);//no error
		obj.overload(-1);//wrong num of args
		obj.overload(-1, 'wrong');//type error
		obj.overload(-1, 'wrong', -1);//wrong num of args
	}
})
.endType();