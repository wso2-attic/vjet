String.prototype.customMethod = function(){};

vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug4791') //< public
.props({
        foo :function(){
                var a = "ABC";//<String
                a.customMethod();
        },
        
        init: function(){
                var txt = "Hello World";//<String
                var h = txt.has("Hello");
                if(!h){

                }
        }
        
})
.endType();
