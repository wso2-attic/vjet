vjo.ctype("vjoPro.dsf.document.Image")
.needs("vjoPro.dsf.Element")
.props({
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
var e = vjoPro.dsf.Element.get(psId);
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
* @param {String} id
*        the id of the Image element
* @param {int} width
*        width of the image
* @param {int} height
*        hight of the image
*/
//> public void resize(String,int,int);
resize : function(psId, piWidth, piHeight) {
var d = document;//<HTMLDocument
var e = d.images[psId]; 
var ow, oh, nw, nh, arw, arh, ar;
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

