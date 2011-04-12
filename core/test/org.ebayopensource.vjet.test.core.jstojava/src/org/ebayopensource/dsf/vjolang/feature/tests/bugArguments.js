vjo.ctype('com.ebay.test.validation.TestTest') //< public
.props({
        //>public void main(String... args) 
        main : function(){
                vjo.sysout.print(this.testFunc1("Hello!!"));
        },
        //>protected String testFunc1(String str)
        testFunc1 : function(str){
                vjo.sysout.println(arguments);
                vjo.sysout.println(arguments.length);
                vjo.sysout.println(arguments.callee);
                vjo.sysout.println(arguments.caller);
                return str;
        }
})
.endType();
