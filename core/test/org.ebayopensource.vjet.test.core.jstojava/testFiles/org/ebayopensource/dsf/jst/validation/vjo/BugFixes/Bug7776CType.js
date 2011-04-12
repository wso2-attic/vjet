vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug7776')
.props({
   foo: function(){
      if(Math.random() > 0.5){
         var scopedVar = 0;
      }
      else if(Math.random() > 0.25){
         var scopedVar = 1;
      }
      else{
         var scopedVar = 2;
      }
   },
   
   bar: function(){
      var scopedVar = 0;
      if(Math.random() > 0.5){
         var scopedVar = 0;
         if(Math.random() > 0.25){
	         var scopedVar = 1;
	     }
      }
   }
})
.endType();