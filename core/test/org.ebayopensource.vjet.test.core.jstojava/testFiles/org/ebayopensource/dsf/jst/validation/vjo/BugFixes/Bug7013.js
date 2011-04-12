vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug7013') //< public
.props({

      //>public void main(String... args) 
      main : function(args){
          var perArr = new Array(2); //< String[]
          perArr[0] = "abc";
          perArr[1] = "abc";
          perArr[0].length;
          
          perArr[1] = 123;
      }
})
.endType();
