vjo.ctype('typecheck.compartible.TypecheckMixedType') //< public
.props({
	
    main:function(){
		var d;//<Date
		var s;//<String
		
		var d_plus_s;//<[Date + String]
		//correct assignments
		d = d_plus_s;
		s = d_plus_s;
		//incorrect ones
		d_plus_s = d;
		d_plus_s = s;
		
		var s_plus_d;//<[String + Date]
		//correct assignments
		d_plus_s = s_plus_d;
		s_plus_d = d_plus_s;
		
		var i;//<int
		var d_plus_s_plus_i;//<[Date + String + int]
		//correct assignments
		d_plus_s = d_plus_s_plus_i;
		s_plus_d = d_plus_s_plus_i;
		//incorrect assignments
		d_plus_s_plus_i = d_plus_s;
		d_plus_s_plus_i = s_plus_d;
		
		var d_plus_s_array;//<([Date + String])[]
		//correct assignments
		d_plus_s_array[0] = d_plus_s;
		d_plus_s_array[0] = s_plus_d;
		d_plus_s_array[0] = d_plus_s_plus_i;
		//incorrrect ones
		d_plus_s_array[0] = d;
		d_plus_s_array[0] = s;
		
		var d_type;//<type::Date
		var s_type;//<type::String
		/* type of mixed type shouldn't be exposed!
		var d_plus_s_type;//<type::([Date + String])
		d_plus_s_type = d_type;
		d_plus_s_type = s_type;
		*/
		
		var d_type_plus_s_type;//<[type::Date + type::String]
		d_type = d_type_plus_s_type;
		s_type = d_type_plus_s_type;
		
		/*mixed type attributed syntax having problem
		var getYear;//<([Date + String]):getYear
		getYear();
		*/
	}
})
.endType();