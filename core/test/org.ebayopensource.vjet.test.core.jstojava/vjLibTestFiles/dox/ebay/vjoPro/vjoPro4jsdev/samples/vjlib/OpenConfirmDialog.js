vjo.ctype("dox.ebay.vjoPro.vjoPro4jsdev.samples.vjlib.OpenConfirmDialog")
.needs("vjoPro.dsf.window.utils.VjWindow")
.props({

//> public boolean openConfirmWin()
openConfirmWin:function(){
return vjoPro.dsf.window.utils.VjWindow.confirm("Are you sure you want to visit http://www.ebay.com?");
}

})
.endType();
