vjo.ctype("org.ebayopensource.dsf.vjolang.feature.tests.bug5515")
.props({
	 	//> public HTMLFormElement foo(String); 
        //> public HTMLFormElement foo(HTMLFormElement);
        foo : function(ref){
                if (typeof(ref) == "string"){
                        return document.forms[ref];
                }else{
                        return ref;
                }       
        },
		//>public void f(String x);
		//>public void f(Number x);
		//>public void f(Date x);
		f:function(x){
			x.length
			x.charCodeAt(3);
			x.toFixed();
			x.getDate();
			
		}

           
})
.inits( function() {
       var myA = this; //< bug5172
       var myB = myA.foo(); //<String
})
.endType(); 