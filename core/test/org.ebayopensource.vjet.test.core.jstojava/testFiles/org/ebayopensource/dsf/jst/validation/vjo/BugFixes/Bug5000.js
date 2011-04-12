vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5000') //< public
.props({
        //> public void foo()
        foo: function() {
			var VALUE_OF = Boolean.prototype.valueOf;
			var toString = Boolean.prototype.toString; 
			
            try {
                var a = VALUE_OF;
            } catch (e) {

            }
        }
})
.endType();
