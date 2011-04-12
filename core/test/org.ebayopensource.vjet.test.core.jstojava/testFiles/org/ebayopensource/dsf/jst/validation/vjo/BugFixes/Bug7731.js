vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug7731')
.props({
   foo: function(){
      var bar = this.bar;//should be a valid ref
   },
   
   bar: function(){//<private void bar()
   }
})
.endType();