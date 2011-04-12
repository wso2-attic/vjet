vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug4690') //< public
.props({
  //>public void doIt() 
  //>public void doIt(String)
  //>public void doIt(int)
  doIt : function(arg){
        var x = null; //<String
        var y = { a: "test", c:"10" };
        this.doIt2(y);
        var z = y.r; // false positive dynamic property should not be error
        var z3 = y['r']; // ok
        var z1 = y.a; // ok
        var z2 = y['a']; // ok


  },
  //>public void doIt2(Object)
  doIt2:function(o){
        o.r = "test";
  }
})
.endType();
