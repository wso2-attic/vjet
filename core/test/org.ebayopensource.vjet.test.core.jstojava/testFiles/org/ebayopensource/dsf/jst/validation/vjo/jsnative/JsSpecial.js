vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.jsnative.JsSpecial')
.props({
		ref:null,
		
        foo: function() {
                var a = true;
                if(a && document){
                        //document exists
                }
                
                var arr = [1,2,3];
                var i = 0; //<Number
                for(i in arr){
                        alert(arr[i]);
                }
        },
        
        foo1: function(){
                var arr = [1,2,3];
                var i = 0; //Number
                for(i in arr){
                        alert(arr[i]);
                }
        },
        
        foo2: function(){
                var arr = [1,2,3];
                var i;
                for(i in arr){
                        alert(arr[i]);
                }
        },
        
        foo3: function(){
        	var y = { a: "test", c:"10" };
        	var z = y.r;
        },
        
         //>public void bar() 
        bar : function(){
                var url = "http://www.ebay.com?a=b"; //<String
                if(url.indexOf("?")<0){

                }
        },
        
        bar1 : function(){
        	var str = "sth"; 
        	String.prototype.customMethod = function(){};
        	str.customMethod();
        },
        
         //>public void bar2() 
        bar2 : function(){
                function bar(){alert("Hello");};
                bar();
        },
        
        //>public void bar3()
        bar3 : function(){
                (this.ref/*<<HTMLAnchorElement*/).style.left = "100px";
        },
        
        bar4: function(){
        	alert(eval.length);
        	//alert(Function.length);
        	var arr = new Array();
        }
})
.inits(function(){
        this.ref = document.getElementById("A");//<<HTMLAnchorElement
})
.endType();
