vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.overriden.Parent")
.protos({
	
	//> public abstract void doIt()
	doIt: vjo.NEEDS_IMPL,
	
	//> public final void sayIt()
	sayIt: function(){
	},
	
	//> public void playIt()
	playIt: function(){
		//shouldn't have narrower visibility when being overriden
	}
})
.endType();