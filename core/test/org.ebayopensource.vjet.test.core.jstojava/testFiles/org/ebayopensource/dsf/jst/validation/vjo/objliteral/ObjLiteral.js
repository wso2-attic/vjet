vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.objliteral.ObjLiteral")
.props({
  
	main: function(){
		var id = 1; //<int
		var ol = {
			id: "object literal" //<String
			,
			age: 10 //<int
			,
			hello: function(str){ //<int hello(String)
				alert(this.id);
				return this.age;
			}
		};
		//should be a valid assignment
		var x = ol.id;//<String
		
		//should be an invalid assignment
		var a = ol.age;//<String
		
		ol.hello(1);
	}
})
.endType();