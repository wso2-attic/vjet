vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.unsupported.MissArg')//<public
.props({
        main: function() {
                var test = new this();//<MissArg
                test.zot('a', 'b');
                test.zot('a');
                test.zotAnno('a', 'b');
                test.zotAnno('b');
        },
        
        foo : function(a,b,c){

        },
        
        bar : function(){
                this.foo("a");
                this.foo("a","b");
                this.foo("a","b","c");
        }
})
.protos({
        zot: function(a, b) {
                a += typeof b === 'string' ? b : "foo";
                vjo.sysout.println(a);
        },
        
        //>public void zotAnno(String, String)
        zotAnno: function(a, b) {
                a += typeof b === 'string' ? b : "foo";
                vjo.sysout.println(a);
        }
})
.endType();
