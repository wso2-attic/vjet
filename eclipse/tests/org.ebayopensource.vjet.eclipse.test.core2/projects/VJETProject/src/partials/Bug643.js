vjo.needs('vjo.Registry');
vjo.needs('vjo.dsf.document.Element');
vjo.needs('vjo.samples.classes.log.Log');
vjo.ctype('partials.Bug643')
.props({

	/**
	* @return void
	* @access public
	* @param {JsOnlyEx2JsModel} poModel
	* 
	*/
	init : function (poModel) {
		//setup logger
		vjo.Registry.put("logger",new vjo.samples.classes.log.Log());

		var self = this, oED = vjo.dsf.EventDispatcher;
		this.model = poModel;
		oED.add(this.model.select,'change',function (evt) {}
			self.changeFrame(evt); 
		});
		var anchors = this.model.anchors;
		for (var i=0; i<anchors.length;i++) {
			oED.add(anchors[i],'click',this.createAnchorOnClick(i));
		}
	},
	
	createAnchorOnClick : function (index) {
		return function () {
			vjo.Registry.get('logger').log('anchor '+index+'clicked');
		};
	},
	
	changeFrame : function (evt) {
		var oElem = vjo.dsf.document.Element;
		//evt is a dsf Event, since we used add to wire it up,
		//as opposed to addEventListener which would return the 
		//native browser event
		var sel = evt.src;
		var fr = oElem.get(this.model.iframe);
		fr.src = sel.value;
    }
}).endType();
