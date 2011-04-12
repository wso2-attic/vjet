vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.rt.otype.CallbackClient")
.needs("org.ebayopensource.dsf.jst.validation.vjo.rt.otype.CallbackRegistry")
.props({
	main: function(){
		var r = this.vj$.CallbackRegistry;//<type::CallbackRegistry
		r.registerOnConnect(function(s, i){
			//error usage on purpose to verify the binding is correct
			i.substring(s);
			s.substring(new Date());
		});
		
		r.registerOnDisconnect(function(s, d){
			//error usage on purpose to verify the binding is correct
			d.substring(s);
			s.substring(d);
		});
	}
}).endType();