vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5297")
.props({
        json:{"name" : "Raja","location":"SJC"},//<<;
        bar : function(){
                alert(this.json.name);
                alert(this.json.location);
        }       
})
.endType();
