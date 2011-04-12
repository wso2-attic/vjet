vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.objliteral.ObjLiteralWithAttributedTypes")
//>needs(org.ebayopensource.dsf.jst.validation.vjo.objliteral.SimpleAttributor)
.props({
  
	main: function(){
		var ol = {
			id: 1 //<SimpleAttributor:intProto
			,
			f: null //<SimpleAttributor:funProto
		};
		
		var x = ol.id;//<int
		var f = ol.f;//<void function()
		f();
		ol.f();
		ol.f.apply(null);
	}
})
.endType();