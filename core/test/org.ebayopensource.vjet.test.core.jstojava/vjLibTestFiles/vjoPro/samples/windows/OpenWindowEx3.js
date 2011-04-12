vjo.ctype('vjoPro.samples.windows.OpenWindowEx3') //< public
.needs('vjoPro.dsf.window.utils.VjWindow')
.props({
/**
* @return boolean
* @access public
*
*/
//> public boolean openWin()
openWin:function(){
vjoPro.dsf.window.utils.VjWindow.open("http://www.ebay.com", "testWin", "width=400,height=600,location=yes,menubar=yes,scrollbars=yes,status=no", false, true, 400, 600);
return false;
}

})
.endType();
