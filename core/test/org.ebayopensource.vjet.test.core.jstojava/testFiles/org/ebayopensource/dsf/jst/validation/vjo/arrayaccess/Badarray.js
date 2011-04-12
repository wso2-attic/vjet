vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.arrayaccess.BadArray").props({
  	
  	//> public void a()
  	a:function(){
        var pros = new String["1", 2, 23.42, true];
    },

    //> public void a1()
    a1:function(){
        var pros = new int[2]{1,2};
    },

        //> public void a2()
    a2:function(){
        var pros = new String[1]{"1", '2'};
    },

    //> public void a3()
    a3:function(){
        var pros = new double[1]{10,20.0};
    }
})
.inits(function(){})
.endType();