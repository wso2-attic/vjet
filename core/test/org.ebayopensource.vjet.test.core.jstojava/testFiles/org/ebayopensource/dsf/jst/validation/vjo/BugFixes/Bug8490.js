vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug8490")
.props({
        //>public void main(String... args) 
        main : function(args){
                this.foo(); // expected error .. .runtime error will happen
                this.foo; // warning no runtime error - ok
                this().foo(); // expected error ... runtime error
                this().foo; // warning no runtime error - ok

        }
})
.protos({

})
.endType();
