eclipse.addEventListener("test", function(a){
	
	alert("print test service")
})


	var storage = {};
		var serviceRegistry = eclipse.ServiceRegistry();
		var pluginRegistry =  eclipse.PluginRegistry(serviceRegistry, storage);
	
		
		
		var plugins = pluginRegistry.getPlugins();
		
		var promise = pluginRegistry.installPlugin("someplugin.html").then(function() {
		  // do something here
		}, function(e) {
			plugins = pluginRegistry.getPlugins();
			pluginRegistry.shutdown();
			
		});
		