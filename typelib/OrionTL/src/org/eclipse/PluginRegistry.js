/**
 * Creates a plugin registry
 */
vjo.ctype('org.eclipse.PluginRegistry') //< public
//< needs(org.eclipse.ServiceRegistry)
.protos({
	//>public Object getPlugins() 
	getPlugins : vjo.NEEDS_IMPL
})
.options({
	metatype:true	
	})
.endType();

