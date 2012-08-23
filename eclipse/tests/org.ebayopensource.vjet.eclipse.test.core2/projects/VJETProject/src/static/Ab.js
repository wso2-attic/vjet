vjo.ctype("static.Ab")
.needs("static.Bb")
.props({
  //>public void foo()
  foo : function() {
  	var x = new Bb()//<static.Bb
  	x.doIt().st
  }
})
.endType();