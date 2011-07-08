vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.mtdinvocation.OverloadMtdInvocation')
.props({
	//>int foo(int)
	//>Date foo(Date)
	foo: function(p){
		return null;
	},
	
	//>int best(Object)
	//>String best(String)
	//>Date best(String, Date)
	//>Error best(String, String, String...)
	best: function(p0, p1){
		return null;
	},
	
	main : function(){
		//>int f(int)
		//>Date f(Date)
		var f = function(p){
			return null;
		}
		
		var i0 = this.foo(1);//<int
		var d0 = this.foo(new Date());//<Date
		
		var i1 = f(1);//<int
		var d1 = f(new Date());//<Date
		
		//more complicated cases involving best match
		var i2 = this.best(new Object());//<int
		var s2 = this.best("");//<String
		var d2 = this.best("", new Date());//<Date
		var e20 = this.best("", "");//<Error
		var e21 = this.best("", "", "");//<Error
	}
})
.endType();