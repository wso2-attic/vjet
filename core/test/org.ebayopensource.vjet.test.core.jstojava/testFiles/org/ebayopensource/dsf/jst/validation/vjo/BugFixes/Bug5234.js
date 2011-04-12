vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5234")
.props({
        //> public float parse()
        parse : function() {
                var psVal = "100,00";
                var v = new String(psVal);
                v = v.replace(/,/g,"");
                v = parseFloat(v);
                return v;
        }
})
.endType();