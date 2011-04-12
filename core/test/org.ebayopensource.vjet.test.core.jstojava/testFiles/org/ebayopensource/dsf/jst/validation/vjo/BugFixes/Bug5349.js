vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5349")
.props({
        msg : "Hello World",
        //> public void init()
        init : function(){
                String.prototype.isAny = function ()
                {
                        var a = arguments, l = a.length, rv = false, aL;
                        for (var i=0; i<l && !rv; i++)
                        {
                                if (typeof(a[i]) == "string"){
                                        rv = (this == a[i]);
                                }
                                else{
                                        //It's an array (of strings)
                                        aL = a[i].length;
                                        for (var j=0; j<aL && !rv; j++){
                                                rv = (this == a[i][j]);
                                        }
                                }
                        }
                        return rv;
                };
        }
})
.endType();
