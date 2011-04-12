vjo.ctype("dox.ebay.vjoPro.vjoPro4jsdev.samples.vjlib.ImagePreloadImage")
.props({
//> public void preload(String psImageUrl)
preload:function(psImageUrl){
if (document.images){// object detection
var img = new Image();
img.src = psImageUrl;
}
}
}).endType();
