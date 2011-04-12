// @Package com.ebay.vjo
vjo.ctype("com.ebay.vjo.VjValidation").endType(); 
(function () {
	
	if(typeof(vjo)!="object"){
		return;
	}
	vjo.validateType = function (pType) {
		//Validate expects, if any
		var s = "";
		if (pType._expects) {
			var oE = vjo._typeMap[pType._expects];
			if (oE) {
				oE = oE.pkg[oE.className];
				if (oE) {
					for (var itm in oE) {
						if (this.isValidProp(itm)) {
							if (!pType[itm]) {
								this.logError("Missing expected properties/methods in type '"+pType._class+"'. Implementation for itype '" + pType._expects + "' properties/methods expected. Missing '" + itm + "'.", pType);
							}
						}
					}
					for (var itm in oE.prototype) {
						if (this.isValidProp(itm)) {
							if (!pType.prototype[itm]) {
								this.logError("Missing expected properties/methods in type '"+pType._class+"'. Implementation for itype '" + pType._expects + "' properties/methods expected. Missing '" + itm + "'.", pType);
							}
						}
					}
				}
			}
		}
	}
	
	vjo._errors = [];
	vjo.logError = function (pMsg,pCls) {
		var tmp = pCls._class || "unknown";
		//TODO - integrate with real logger.
		var e = "";
		this._errors[this._errors.length] = e = {type:"ERROR", msg:pMsg, source:tmp};
		//Error
		if (window.console) {
			window.console.error(e.msg);
			window.console.dir(e);
		}
	}
		
})();