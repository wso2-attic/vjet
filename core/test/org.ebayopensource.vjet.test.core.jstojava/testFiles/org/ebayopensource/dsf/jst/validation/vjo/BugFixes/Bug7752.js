vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug7752')
.props({
   foo: function(){
      if(Math.random() > 0.5){
         var scopedVar = 0;//<int
      }
      else if(Math.random() > 0.25){
         var scopedVar = 1;//<float
      }
      else{
         var scopedVar = 2;//<double
      }
   },
   
   bar: function(){
      var scopedVar = 0;//<int
      if(Math.random() > 0.5){
         var scopedVar = 0;//<float
         if(Math.random() > 0.25){
	         var scopedVar = 1;//<double
	     }
      }
   }
})
.endType();