

vjo.ctype('typecheck.array.CorrectArray3') //< public
.props({
    //> public void a()
    a:function(){
    	var pros = new Array(3);
    },
    
    //> public void a1()
    a1:function(){
	var pros = new Array("10","20","30");
    },
    
 	//> public void a2()
    a2:function(){
	var pros = new Array(10,20,30);
    }
    
})
.endType();
