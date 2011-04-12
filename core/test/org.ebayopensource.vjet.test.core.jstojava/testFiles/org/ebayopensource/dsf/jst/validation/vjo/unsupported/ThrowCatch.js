vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.unsupported.ThrowCatch')//<public
.protos({
	 foo : function(a ,b){
               var EXCEPTION_DATA = "String exception";
                var e;
                var caught = false;
                try
                {   
                        throw EXCEPTION_DATA;  
                }
                catch (e if true)
                {
                        caught = true;
                        alert(caught);
                }
     },
     
     bar: function(){
     	var exception = "No exception thrown";
        var result = "Failed";
        try {
          var s = new String("Not a Boolean");
        } catch ( e ) {
          result = "Passed!";
          exception = e.toString();
        }
     }
})
.endType();