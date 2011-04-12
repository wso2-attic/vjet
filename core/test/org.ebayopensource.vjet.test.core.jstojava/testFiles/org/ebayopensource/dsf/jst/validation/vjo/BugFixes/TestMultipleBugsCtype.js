vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.TestMultipleBugsCtype') //< public
.props({
       foo : function(){
                var d1 = new Date(); 
                var d2 = new Date();
                var d3 = d1-d2;
                if (d1>d2){

                }
         },
         
         foo1 : function(){
                var myBoolean=new Boolean(); 
                myBoolean=new Boolean(""); 
                myBoolean=new Boolean("true");
                myBoolean=new Boolean("false");
                myBoolean=new Boolean("Richard");
                myBoolean=new Boolean(0);
                myBoolean=new Boolean(null);
                myBoolean=new Boolean(false);
                myBoolean=new Boolean(NaN);
                myBoolean=new Boolean(true);
        },
        
      //>public void foo2() 
      foo2 : function(){
              alert(new Number()); //Bug5073
              alert(new String());
              alert(new Number(''));
      },
      
      //>public void foo3() 
      foo3 : function(){
              alert(Math.abs("a string primitive")); //BUG 5072
              alert(Math.abs("-1e-1"));
              alert(Math.abs("0xff"));
              alert(Math.abs("077"));
              alert(Math.abs("Infinity"));
              alert(Math.log());
              alert(Math.max());
              alert(Math.max( "-99","99"));
              alert(Math.max("a string primitive"));
              alert(Math.max("a string", Number.POSITIVE_INFINITY));
              alert( Math.min());
              alert(Math.min( "-99","99"));
              alert( Math.pow());
              alert( Math.pow('2','32'));
              alert( Math.round() );
              alert(Math.round('.99999') );
      },
      
      //>public void foo4() 
      foo4 : function(){
                String.fromCharCode(100); //BUG 5068
      },
      
      //>public void foo5() 
      foo5 : function(){
                        alert(new Date(1011010101)); //BUG5067
                        alert(new Date( 1899, 0, 1 ));
      },
      
      //>public void foo6() 
      foo6 : function(){
              alert( 'a'.replace(/a/g, "A")); //BUG 5064
      },
      
      foo7 : function(){
                var f = Function.call(self, 'return "Hello"'); //Bug 5021
            alert(f());
        },
        
        foo8 : function(){
                var a = "Hello World"; //Bug 5014
                alert(/\s/.test(a));
        },
        
        //>public void foo() 
        foo9 : function(){
                var flashVars = [1,2,3]; //Bug5142
                for (var name in flashVars){

                }
        },        
        
        //>public void load(String psUrl)
        load : function(psUrl) {
                if(document.createStyleSheet){                       //Bug5108
                        document.createStyleSheet(psUrl);
                }else {
                        var head = document.getElementsByTagName('head')[0], style = document.createElement("link");
			            style.rel =  "stylesheet";
			            style.type = "text/css";
			            style.href = psUrl;
			            head.appendChild(style);
                }
        }
        
        
      
      
      
})
.endType();