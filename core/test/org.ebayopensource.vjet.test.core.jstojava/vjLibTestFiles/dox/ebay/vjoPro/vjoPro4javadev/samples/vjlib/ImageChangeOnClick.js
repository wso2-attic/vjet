vjo.ctype("dox.ebay.vjoPro.vjoPro4javadev.samples.vjlib.ImageChangeOnClick")
.needs("vjoPro.dsf.document.Image")
.props({

//> public void loadImg(String psId, String psURL)
loadImg:function(psId, psURL){
vjoPro.dsf.document.Image.load(psId, psURL);
}

})
.endType();
