vjo.ctype('com.ebay.test.Sample') //< public
.props({
        P1 : Arguments.$missing$,

        foo :function(){
                var a = "ABC";
                a.customMethod();
        }
})
.endType();
