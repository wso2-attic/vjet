vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.rtnflow.Unreachable') //< public
.props({
		//>public int foo()
        foo: function() {
        		var redundant = true;//<boolean
                return 1;
                return 2;
        },
        
        //>public void bar()
        bar: function() {
        		var redundant = true;//<boolean
                return;
                return;
        },
        
        //>public String foo1(boolean)
        foo1: function(condition) {
        		if(condition){
                	return "1";
                }
                return "2";
        },
        
        //>public String foo1(boolean)
        foo2: function(condition) {
                if(condition){
                	return "1";
                }
                else{
                	return "2";
                }
                return "3";
        }
})
.endType();