vjo.ctype('vjoPro.samples.images.Animation') //< public
.needs(['vjoPro.dsf.utils.Object','vjoPro.dsf.document.Image'])
.protos({
/**
* @access public
* @param {String} psId
* @param {String[]} paImageUrls
*/
//> public constructs(String psId,String[] paImageUrls)
constructs : function (psId, paImageUrls) {
if (document.images){// object detection
this.sId = psId;
// add image
this.aImageUrls = paImageUrls;
// preload image
for(var i = 0; i < this.aImageUrls.length; ++i) {
vjoPro.dsf.document.Image.preload(this.aImageUrls[i]);
}
}
},
/**
* @return void
* @access public
*/
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
})
.endType();
