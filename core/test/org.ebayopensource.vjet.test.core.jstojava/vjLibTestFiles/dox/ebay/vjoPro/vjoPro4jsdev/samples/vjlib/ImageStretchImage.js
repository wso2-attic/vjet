vjo.ctype("dox.ebay.vjoPro.vjoPro4jsdev.samples.vjlib.ImageStretchImage")
.needs('vjoPro.dsf.Element')
.props({

//> public void resize(String psId, int piWidth, int piHeight)
resize:function(psId, piWidth, piHeight){
if (document.images){// object detection
var e = vjoPro.dsf.Element.get(psId);
if (e){
// set size, client bug workaround
e.width = e.width;
e.height = e.height;
// adjust size
e.width += piWidth;
e.height += piHeight;
}
}
}
}).endType();
