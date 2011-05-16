 vjo.ctype("org.ebayopensource.vjet.rt.tests.jsdebugger.HelloV4")
.props({
    /**      
      * @return boolean
      * @access public
     */
    //> public boolean helloWorld()
     helloWorld:function() {
         var aForm = document.forms["myform"];
         var url = aForm.action;
         url += "/HelloV4";
         aForm.action = url;
         var j = 5;
         j *= 2;
         aForm.abc.value = j;
         aForm.submit();
    }
})
.endType();
