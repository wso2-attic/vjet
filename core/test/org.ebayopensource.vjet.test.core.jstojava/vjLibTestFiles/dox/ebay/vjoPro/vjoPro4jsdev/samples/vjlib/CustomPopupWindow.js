vjo.ctype("dox.ebay.vjoPro.vjoPro4jsdev.samples.vjlib.CustomPopupWindow")
.needs("vjoPro.dsf.window.utils.VjWindow")
.props({

//> public boolean openWin()
openWin:function(){
vjoPro.dsf.window.utils.VjWindow.open("http://www.ebay.com", "testWin", "width=400,height=600,location=no,menubar=no,scrollbars=no,status=yes", false, true, 400, 600);
return false;
}

})
.endType();
