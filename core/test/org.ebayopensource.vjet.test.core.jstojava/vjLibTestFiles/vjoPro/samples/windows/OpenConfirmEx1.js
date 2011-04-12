vjo.ctype('vjoPro.samples.windows.OpenConfirmEx1') //< public
.needs('vjoPro.dsf.window.utils.VjWindow')
.props({
/**
* @return boolean
* @access public
*
*/
//> public boolean openConfirmWin()
openConfirmWin:function(){
return vjoPro.dsf.window.utils.VjWindow.confirm("Are you sure you want to go to http://www.ebay.com?");
}

})
.endType();
