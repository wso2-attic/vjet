vjo.ctype("dox.ebay.vjoPro.vjoPro4javadev.samples.vjlib.ImageLoadingFunction")
.needs("vjoPro.samples.images.Image")
.props({

//> public void loading(String psId)
loading:function(psId){
for(var i = 0; i < document.images.length; ++i) {
if (document.images[i].complete) {
this.loadCount++;
vjoPro.samples.images.Image.resize(psId, 100, 0);
}
}
if (this.loadCount < document.images.length) {
setTimeout(this.loading(psId), 1000);
}
alert(this.loadCount);
//		vjoPro.samples.images.Image.resize(psId, this.loadCount * 100, 0);
},
loadCount : 0
})
.endType();
