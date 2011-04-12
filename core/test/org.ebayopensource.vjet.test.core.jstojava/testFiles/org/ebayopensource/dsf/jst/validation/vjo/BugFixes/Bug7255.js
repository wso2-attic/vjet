vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug7255")
.protos({
    i: undefined,    //< final int
    str: null,  //< final String
    a: new Array(2), //< final Array
    constructs:function(){
        this.i=10;          // ok since i is undefined
        this.str = 'hello'; // ok since str is undefined
        this.a = null ;     // should be an error
    }
})
.props({
	 x: -1,        		//< private final int
     s: 'nope',     	//< final String
     o: new Object(),	//< final Object
     y: 'str' //<private final int
})
.inits(function(){
     this.x = 10 ;
     this.s = 'xyz' ;
     this.o = null ;
     this.y = 100;
})
.endType();