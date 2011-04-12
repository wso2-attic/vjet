vjo.ctype("partials.Ab")
.needs("partials.Bb")
.props({
  //>public void foo()
  foo : function() {
        var x = new Bb(); //<partials.Bug2149TypeB
        x.doIt().st
  }
})
.endType();
