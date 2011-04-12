vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug9133")
.needs('vjoPro.dsf.Element')
.props({
	
	//> public void onKeyEnter(Object ev, String elId)
	onKeyEnter : function(ev, elId){
		var el = vjoPro.dsf.Element.get(elId);	//< HTMLElement	
		if(el){
			 if(el.dispatchEvent){
	            var oEvent = document.createEvent("MouseEvents");
	            oEvent.initMouseEvent("click", true, true,window, 1, 1, 1, 1, 1, false, false, false, false, 0, el);
	            el.dispatchEvent(oEvent);
	          }
	        else if(el.fireEvent) { 
	           el.fireEvent("onclick");
	         }
		}
	}
})
.endType();
