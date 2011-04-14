vjo.otype('org.dojotoolkit.DojoAuxiliary') //< public
.defs({
	animArgs : {
		node : null, //<Node
		properties : null, //<Object
		duration : 0, //<Number?
		easing : null, //<(boolean easing(int index))?		
		onEnd : null //<(void onEnd(Node node))?
		
	},

	xhrArgs : {
		error : null, //<(void error())?		
		handle : null, //<(void handle())?		
		load : null, //<(void load())?		
		content : null, //<Object?
		form : null, //<Node?
		handleAs : null, //<String?
		headers : null, //<Object?
		preventCache : false, //<boolean?
		sync : false, //<boolean?
		timeout : 0, //<int?
		url : null //<String?
	},
	
	fadeArgs : {
		easing: null, //<<(boolean easing(int))?
		duration: 0, //<Number?
		node: null //<{String|Node}
	},
	
	coords : {
		l : 0, //<int?
		t : 0, //<int?
		w : 0, //<int?
		h : 0, //<int?
		x : 0, //<int?
		y : 0 //<int?	
	},
	
	margin : {
		l : 0, //<int?
		t : 0, //<int?
		w : 0, //<int?
		h : 0 //<int?	
	},
	
	position : {
		w : 0, //<int
		h : 0, //<int
		x : 0, //<int
		y : 0  //<int
	},
	
	CSS2Properties : {
		background: null, //<Object?
		'background-attachment': null, //<Object?
		'background-color': null, //<Object?
		'background-image': null, //<Object?
		'background-position': null, //<Object?
		'background-repeat': null, //<{boolean|String}?
		border: null, //<Object?
		width : 0, //<{int|String}?
		height : 0 //<{int|String}?
		//more ...		
	}
})
.endType();
