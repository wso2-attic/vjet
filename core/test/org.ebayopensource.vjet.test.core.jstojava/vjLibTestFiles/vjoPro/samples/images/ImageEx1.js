vjo.ctype('vjoPro.samples.images.ImageEx1') //< public
.needs('vjoPro.dsf.document.Image')
.props({
/**
* @return void
* @access public
* @param {String} psId
* @param {String} psURL
*
*/
//> public void loadImg(String psId,String psURL)
loadImg:function(psId, psURL){
vjoPro.dsf.document.Image.load(psId, psURL);
}

})
.endType();
