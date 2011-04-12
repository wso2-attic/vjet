vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug6759")//< abstract
.props({
	//>public void adf()
	adf: vjo.NEEDS_IMPL,
	
	//>public abstract void adf2()
	adf2: vjo.NEEDS_IMPL,
	
	prp1: null//<protected abstract
})
.protos({

	//>private abstract void adf3()
	adf3: vjo.NEEDS_IMPL,
	
	adf4: function(){
		this.adf3(); alert(this.prp2);
	},
	
	prp2: null//<private abstract
})
.endType();