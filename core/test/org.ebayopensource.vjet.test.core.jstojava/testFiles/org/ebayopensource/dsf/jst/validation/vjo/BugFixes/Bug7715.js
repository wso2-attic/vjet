vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug7715')
.props({
        prop1:10,
        //>public void foo(String s, String s1) 
        foo : function(a,si){
                var x = [this.prop1];
                var x2 = [this.prop2]; // (error case) expected error got
                var x3 = {a:this.prop1};
                var x4 = {a:this.prop2}; // (error case) expected error got
   }
})
.endType();
