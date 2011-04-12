// @Package com.ebay.vjo

if(typeof(vjo)=="undefined"){
	var vjo = {};
}

vjo.global = this;
vjo._bScope = null;
vjo.createPkg = function(clz) {
	var names = clz.split('.'), len = names.length;
	var pkg = this.global; //TODO: update with scope
	for (var i=0;i<len-1 && pkg && names[i];i++){
		pkg = (pkg[names[i]]) ? pkg[names[i]] : pkg[names[i]] = {};
	}
	return {pkg:pkg,className:(len>0)?names[len-1]:""};
};

vjo.needs = function (clz,shortName) {
    if (!clz) return;
    if (!vjo._bScope) {
        vjo._bScope = {};
    }
    var pObj = vjo.createPkg(clz);
	if (pObj.pkg[pObj.className]) {
		if (typeof shortName == "string" && shortName!="") 
        	vjo._bScope[shortName] = pObj.pkg[pObj.className];
        else
        	vjo._bScope[pObj.className] = pObj.pkg[pObj.className];
    }
};
vjo.needsLib = function () {
};

vjo.type = function (clz) {
	var base = function() {
		//assign needed types from class
	    this.b = base.b;
	    //add static class name to this.b
	    
		if (this.base) {
			this.base.parent = this;
		}
		if (this.constructs) {
			var rv = this.constructs.apply(this,arguments);
			if (rv) {
				return rv;
			}
		}
		// jce: placeholder to fix lint error
		return null;
	};
	base.props = function (obj) {
		for (var i in obj) {	
			if (i!='props' && i!='protos' && i!='inherits' && i!='prototype' && i!='inits' && i!='satisfies' && i!='satisfiers' && i!='b') {
				base[i] = obj[i];
			}
		}
		return base;
	};
	function createBaseMethod (clz,supClz,name) {
		clz.prototype.base[name] = function() {
			var that = this.parent, supBase = (supClz.prototype && supClz.prototype.base) ? 
					supClz.prototype.base[name] : null, curBase;
			if (supBase) {
				curBase = that.base[name];
				that.base[name] = supBase;
			}
			var scp = (this.parent) ? this.parent : this;
			var rv = supClz.prototype[name].apply(scp,arguments);
			if (curBase) {
				that.base[name] = curBase;
			}
			return rv;
		};
	}
	base.protos = function (obj,supType) {
		for (var i in obj) {
			if (i!='base' && i!='b' && (!supType||i!='constructs')) {
				if (supType && supType.prototype && supType.prototype[i] && typeof obj[i] == 'function') {
					createBaseMethod(base,supType,i);
				}
				if ((!supType && !base.prototype[i]) && base.prototype.base && base.prototype.base[i]) {
					base.prototype[i] = function () {
						base.prototype.base[i].apply(this,arguments);
					};
				} else if (!(supType && base.prototype[i])){
					base.prototype[i] = obj[i];
				}
			}
		}
		return base;
	};
	function createBase(clz,type) {
		return (function () {
			var supBase = type.prototype.base || type, constructs = this.constructs, curBase = this.base;
			if (type.prototype.constructs && constructs) {
					this.constructs = type.prototype.constructs;
			}
			this.base = function () {
				supBase.apply(this,arguments);
			};
			type.apply(this,arguments);
			if (constructs) {
					this.constructs = constructs;
			}
			this.base = curBase;
		});
	}
	base.inherits = function (supClass) { //check order if inherits is called after proto or props
		var opkg = vjo.createPkg(supClass);
		var type = opkg.pkg[opkg.className];
		base.prototype.base = createBase(base,type);
		base.protos(type.prototype,type);
		base.props(type);
		return base;
	};
	base.singleton = function () {
		//TODO: self instantiate
		return base;
	};
	base.inits = function (func) {
		//the class may have already been created, so there's no need
		//to call static initializer once again.
		var pObj = vjo.createPkg(clz);
		if (typeof pObj.pkg[pObj.className] == 'function') {
			func.call(this);
		}
		return base;
	};
	base.satisfies = function (type) {
		//TODO:
		//base.satisfies.type = type;
		return base;
	};
	base.satisfiers = function (type) {
		//TODO:
		return base;
	};
	base.makeFinal = function () {
		//TODO:
		return base;
	};
	
	//setup shorthand scope, based on needs
	base.b = vjo._bScope;
	vjo._bScope = null;
	
	//debugger;
	//if class not specified, return class
	if (!clz) return base;
	
	var pObj = vjo.createPkg(clz);
	//if class already exists, just return the type. do not override existing class
	return (pObj.pkg[pObj.className])? base : (pObj.pkg[pObj.className] = base); 
};