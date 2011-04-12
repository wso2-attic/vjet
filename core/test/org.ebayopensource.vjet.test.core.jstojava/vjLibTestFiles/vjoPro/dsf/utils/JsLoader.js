vjo.ctype("vjoPro.dsf.utils.JsLoader")
.props({
	queue : [],
	pending : null,
	//> public void load(String psUrl, Object poCallback, Object poScope)
	load : function(psUrl,poCallback, poScope) {
		var request = {url : psUrl, callback : poCallback, scope : poScope}, head, stag;
		var t = this; //<type::JsLoader
		if(t.pending){
			t.queue.push(request);
			return;
		}
		this.pending = request;
		head = document.getElementsByTagName('head')[0];
		stag = document.createElement('script');
		stag.onload = stag.onreadystatechange
			= function(){
				if(!this.readyState || this.readyState == "loaded" 
					|| this.readyState == "complete"){
					t.oncomplete();
					// Handle memory leak in IE
					stag.onload = stag.onreadystatechange = null;
					head.removeChild(stag);
				}
			};
		stag.type = "text/javascript";
		stag.src = psUrl;
		head.appendChild(stag);
	},
	//> private void oncomplete()
	oncomplete : function(){
		var t = this, o = t.pending;
		if(o.callback){
			o.callback.call(o.scope||window);
		}
		t.pending = null;
		if(t.queue.length > 0)
		{
			var request = this.queue.shift();
			t.load(request.url, request.callback, request.scope);
		}
	}
})
.endType();
