vjo.ctype('vjoPro.samples.windows.OpenWindowEx6') //< public
.needs('vjoPro.dsf.window.utils.VjWindow')
.protos({
m_winObj:null,
m_winName:null,

/**
* @return void
* @access public
* @param {String} psName
*
*/
//> public constructs(String psName)
constructs : function(psName) {
this.m_winName = psName;
},
/**
* @return boolean
* @access public
*
*/
//> public boolean openWin()
openWin:function(){
this.m_winObj = vjoPro.dsf.window.utils.VjWindow.open("http://www.ebay.com", this.m_winName, "width=600,height=800,location=yes,menubar=yes,scrollbars=yes,status=no", false, false, 600, 800);
return false;
},

/**
* @return boolean
* @access public
*
*/
//> public boolean closeWin()
closeWin:function(){
if (this.m_winObj) {
this.m_winObj.close();
}
return false;
}

})
.endType();
