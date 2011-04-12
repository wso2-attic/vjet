vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.jsnative.AnonymousFunction')
.props({
       //>public void foo() 
        foo : function(){
                function bar(){alert("Hello");};
                bar();
        },
        
        //>public void foo2()
        foo2 : function(){
        	function bar2(name){this.name = name;};
        	var bar2Obj = new bar2('Raja');
        }
})
.endType();
