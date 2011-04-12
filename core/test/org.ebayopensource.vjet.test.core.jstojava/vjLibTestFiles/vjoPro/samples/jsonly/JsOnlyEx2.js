vjo.ctype('vjoPro.samples.jsonly.JsOnlyEx2') //< public
.needs(['vjoPro.Registry','vjoPro.dsf.document.Element',
'vjoPro.samples.classes.log.Log'])
.props({
model:null,//<Object
/**
* @return void
* @access public
* @param {JsOnlyEx2JsModel} poModel
*
*/
//> public void init(JsOnlyEx2JsModel poModel)
init : function (poModel) {
//setup logger
vjoPro.Registry.put("logger",new vjoPro.samples.classes.log.Log());

var self = this, oED = vjoPro.dsf.EventDispatcher;
this.model = poModel;
oED.add(this.model.select,'change',function (evt) { self.changeFrame(evt); });
var anchors = this.model.anchors;
for (var i=0; i<anchors.length;i++) {
oED.add(anchors[i],'click',this.createAnchorOnClick(i));
}

},
createAnchorOnClick : function (index) {
return function () {
vjoPro.Registry.get('logger').log('anchor '+index+' clicked');
};
},
changeFrame : function (evt) {
var oElem = vjoPro.dsf.document.Element;
//evt is a dsf Event, since we used add to wire it up,
//as opposed to addEventListener which would return the
//native browser event
var sel = evt.src;
var fr = oElem.get(this.model.iframe);
fr.src = sel.value;

}
})
.endType();
