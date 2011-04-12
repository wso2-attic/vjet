vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.javaone.MyJsLibrary') //< public
.props({
	
	//>private void(String,String)
	update:function(id, text){ 
		document.getElementById(id).innerHTML = text
	},
	
	//>public void echo(String)
	echo:function(id){
		var str = document.getElementById(id).innerHTML;
		vjo.sysout.println(str);
	}
	
})
.endType();