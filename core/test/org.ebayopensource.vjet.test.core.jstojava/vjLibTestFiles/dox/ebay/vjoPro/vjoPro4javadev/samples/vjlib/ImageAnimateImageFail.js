vjo.ctype("dox.ebay.vjoPro.vjoPro4javadev.samples.vjlib.ImageAnimateImageFail")
.needs("vjoPro.dsf.utils.Object")
.needs('vjoPro.dsf.document.Image')
.needs('vjoPro.samples.images.Image')
.protos({

//> public void constructs(String psId, String [] paImageUrls)
constructs : function (psId, paImageUrls) {
if (document.images){// object detection
this.sId = psId;
// add image
this.aImageUrls = paImageUrls;
// preload image
for(var i = 0; i < this.aImageUrls.length; ++i) {
}
}
},

//> public void animate()
animate:function(){
if (document.images){// object detection
if (this.iCurrentIndex > this.aImageUrls.length - 1) {
this.iCurrentIndex = 0;
}
setTimeout(vjoPro.dsf.utils.Object.hitch(this,"animate"),  500);
}
},
iCurrentIndex : 0
})
.endType();
