vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.BugPrototype')
.protos({

	toRemove: 100,
	
	foo: function(){
		Date.prototype.getTime();
		alert(this.toRemove);
		delete this.toRemove;
		alert(this.toRemove);
		delete this.vj$.BugPrototype.prototype.toRemove;
		alert(this.toRemove);
		
		
		alert(this.prototype);
		var fun = function(){};
		alert(fun.prototype);
	}
})
.endType();