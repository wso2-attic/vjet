vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5483")
.props({
        elm : null,
        foo : function(){
                var d = this.elm.style; 
        }  
})
.inits(function(){
        this.elm = document.getElementById("a");
})
.endType();
