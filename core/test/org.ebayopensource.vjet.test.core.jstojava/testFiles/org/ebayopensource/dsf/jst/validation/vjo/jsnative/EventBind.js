vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.jsnative.EventBind")
.props({
		//>public void foo() 
        foo : function(){
                var stag = document.createElement('script');//<<HTMLScriptElement
                stag.onload = stag.onreadystatechange = function(){
                        var a = this.readyState;
                };              
        }
})
.endType();