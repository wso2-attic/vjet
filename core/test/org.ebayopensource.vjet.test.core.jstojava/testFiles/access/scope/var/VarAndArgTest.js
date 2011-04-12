vjo.ctype('access.scope.var.VarAndArgTest')
.props({
	main: function(){
		var x;//<int
		
		var f = function(x, y){//<void f(Date, Date)
			x.getYear();
			y.getYear();//fwd referencing
		}
	
		var y;//<int
	}
})
.endType();