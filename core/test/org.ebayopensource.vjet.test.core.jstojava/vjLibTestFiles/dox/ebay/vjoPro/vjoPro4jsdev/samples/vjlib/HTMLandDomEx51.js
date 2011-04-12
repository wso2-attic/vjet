vjo.ctype("dox.ebay.vjoPro.vjoPro4jsdev.samples.vjlib.HTMLandDomEx51")
.needs('vjoPro.dsf.Element')
.props({

//> public void toggle(String psShowAllAnchorId, String psHideAllAnchorId)
toggle:function(psShowAllAnchorId, psHideAllAnchorId){
vjoPro.dsf.Element.toggleHideShow(psShowAllAnchorId);
vjoPro.dsf.Element.toggleHideShow(psHideAllAnchorId);
}

}).endType();
