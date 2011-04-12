vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5305")
.props({
		//>public void encode(String)
        encode: function(pInStr) {
                var rcode = encodeURIComponent(pInStr);
				rcode = rcode.replace("a", "b");
        }       
})
.endType();
