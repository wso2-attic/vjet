vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.jsnative.ForwardRef")
.props({
	 
	main: function(){
		late("");//should cause error
	
		
		function late(d){//<void late(Date)
			evenLate.getYear();//should be tolerated
		}
		
		var evenLate = null;//<Date
	}
})
.endType();