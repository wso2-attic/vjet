vjo.ctype("vjoPro.dsf.docx.Imagex")
.needs("vjoPro.dsf.docx.Elementx")
.props({
E : vjoPro.dsf.docx.Elementx,
/**
* Loads a image for a specified <code>Image</code> element.
*
* @param {String} id
*        the id of the Image element.
* @param {String} url
*        a string url of the image path.
*/
//> public void load(String,String);
load : function(psId, psURL) {
var e = this.E.get(psId);
if (e)
{
e.src = psURL;
}
},

/**
* Loads a image before the <code>Image</code> element be completely loaded.
*
* @param {String} url
*        A string url of the image path.
*/
//> public void preload(String);
preload : function(psURL) {
new Image().src = psURL;
},

/**
* Resizes the size of the image.
*
* @param {String or HtmlImage} ref
*        the id of the Image element
* @param {int} width
*        width of the image
* @param {int} height
*        hight of the image
*/
//> public void resize(String,int,int);
//> public void resize(HTMLImageElement,int,int);
resize : function(ref, piWidth, piHeight) {
var d = document, ow, oh, nw, nh, arw, arh, ar;
var e;
if (typeof(ref) == "string"){
e = d[ref]||d.images[ref];
}
else
e = ref;
if(e){
//getting original width and height.
ow = e.width;
oh = e.height;
//calcualting aspect ratio
arw = ow/piWidth;
arh = oh/piHeight;
ar = (arw> arh) ? arw : arh;
if(ar >= 1){
nw = ow/ar;
nh = oh/ar;
}else{
nw = ow;
nh = oh;
}
e.width = nw;
e.height = nh;
}
}
})
.endType();

