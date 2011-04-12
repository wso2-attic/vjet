vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.rt.ctype.BadCTypeB")
.protos({

	//>private void privateInstanceNotInvoked()
	privateInstanceNotInvoked: function(){
		this.privateInstanceButInvoked();
	},
	
	//>protected void protectedInstanceNotInvoked()
	protectedInstanceNotInvoked: function(){
	},
	
	//>public void protectedInstanceNotInvoked()
	publicInstanceNotInvoked: function(){
	},
	
	defaultInstanceNotInvoked: function(){
	},
	
	//>private void privateInstanceButInvoked()
	privateInstanceButInvoked: function(){
	}
})
.props({
	//>private void privateStaticNotInvoked()
	privateStaticNotInvoked: function(){
		this.privateStaticButInvoked();
	},
	
	//>protected void protectedStaticNotInvoked()
	protectedStaticNotInvoked: function(){
	},
	
	//>public void protectedStaticNotInvoked()
	publicStaticNotInvoked: function(){
	},
	
	defaultStaticNotInvoked: function(){
	},
	
	//>private void privateStaticButInvoked()
	privateStaticButInvoked: function(){
	}
})
.endType();