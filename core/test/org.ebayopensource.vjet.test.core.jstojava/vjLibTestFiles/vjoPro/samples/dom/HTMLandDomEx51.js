vjo.ctype('vjoPro.samples.dom.HTMLandDomEx51') //< public
.needs('vjoPro.dsf.document.Element')
.props({
/**
* @return void
* @access public
* @param {String} psShowAllAnchorId
* @param {String} psHideAllAnchorId
*
*/
//> public void toggle(String psShowAllAnchorId,String psHideAllAnchorId)
toggle:function(psShowAllAnchorId, psHideAllAnchorId){
vjoPro.dsf.document.Element.toggleHideShow(psShowAllAnchorId);
vjoPro.dsf.document.Element.toggleHideShow(psHideAllAnchorId);
}

})
.endType();
