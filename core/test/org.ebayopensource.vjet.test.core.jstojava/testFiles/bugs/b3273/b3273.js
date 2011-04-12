vjo.ctype('bugs.b3273.b3273')
.props({
         main: function() { //< public void main (String ... arguments) 
                        var x = arguments[0];//<String
                        vjo.sysout.println(x);

                        document =
vjo.Parser.parse("<html><body></body></html>");
//                      document =  vjo.Parser.parse("http://www.ebay.com");

                        var div = document.createElement("div"); //<HTMLElement
                         document.insertBefore(div, document.body);

                        div.innerHTML=x;
                        div.id = "foo";
                        vjo.sysout.println(document.getElementById("foo"));

        } 
})
.endType();
