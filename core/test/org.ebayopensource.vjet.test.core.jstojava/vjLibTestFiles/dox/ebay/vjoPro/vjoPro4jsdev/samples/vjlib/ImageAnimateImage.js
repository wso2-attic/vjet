vjo.ctype("dox.ebay.vjoPro.vjoPro4jsdev.samples.vjlib.ImageAnimateImage")
.needs("vjoPro.dsf.utils.Object")
.needs("vjoPro.dsf.document.Image")
.needs("dox.ebay.vjoPro.vjoPro4jsdev.samples.vjlib.ImagePreloadImage")
.protos({

//> public void constructs(String psId, String [] paImageUrls)
constructs : function (psId, paImageUrls) {
if (document.images){// object detection
this.sId = psId;
// add image
this.aImageUrls = paImageUrls;
// preload image
for(var i = 0; i < this.aImageUrls.length; ++i) {
dox.ebay.vjoPro.vjoPro4jsdev.samples.vjlib.ImagePreloadImage.preload(this.aImageUrls[i]);
}
}
},

//> public void animate()
animate:function(){
if (document.images){// object detection
if (this.iCurrentIndex > this.aImageUrls.length - 1) {
this.iCurrentIndex = 0;
}
vjoPro.dsf.document.Image.load(this.sId, this.aImageUrls[this.iCurrentIndex++]);
setTimeout(vjoPro.dsf.utils.Object.hitch(this,"animate"),  500);
}
},
iCurrentIndex : 0
}).endType();
