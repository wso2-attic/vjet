vjo.ctype("org.ebayopensource.dsf.vjolang.feature.tests.bug5172")
.props({
	//private String foo();
    foo : function(){
	    var date = new Date();//<Date
           date.toString();
    
    }
           
})
.inits( function() {
       var myA = this; //< bug5172
       var myB = myA.foo(); //<String
})
.endType(); 