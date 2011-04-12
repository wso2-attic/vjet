vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.attributed.AttributedFunctionParam")
//> needs(org.ebayopensource.dsf.jst.validation.vjo.attributed.SimpleAttributor)
.props({
	
	f1 : function(fa){//<void f1(SimpleAttributor::date2numberProp)
	
	},
	
	main: function(){
		this.f1(function(d){
			return d.toYear();
		});
		
		var f2 = function(d){//<SimpleAttributor::date2numberProp
			
		};
		
		var i = f2(new Date());//<int
		
		//error case to verify the binding
		var s = f2("");//<String
	}
})
.endType();