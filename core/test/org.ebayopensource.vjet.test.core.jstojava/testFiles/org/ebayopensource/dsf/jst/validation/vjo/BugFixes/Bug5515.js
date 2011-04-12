vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5515")
.props({
	//>public HTMLFormElement foo(String)
	//>public HTMLFormElement foo(HTMLFormElement)
	foo: function(ref){
		if(typeof(ref) == "string"){
			var rtnVal = document.forms[ref];//<HTMLFormElement
			return rtnVal;
		}
		else{
			return ref;
		}
	}
})
.endType();