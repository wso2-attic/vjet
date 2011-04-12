if (typeof(DLC) == "undefined") {
	var DLC = new DsfLiveConnect(DLC_HOST, DLC_PORT);
}

function DsfLiveConnect(host, port) {
	this.dlcHost = host;
	this.dlcPort = port;
	this.dlcHttpBaseUrl = "http://" + this.dlcHost + ":" + this.dlcPort + "/";
	this.forbiddenPrefixs = [];
	this.forbiddenPaths = [];

	this.registerPrefix = function(prefix) {
		this.forbiddenPrefixs.push(prefix);
	}

	//send any message to DLC server
	this.sendMessage = function(message) {
		var ref = message.indexOf(":")>-1?message.split(":")[1]:undefined;
		var isInternal = false;
		if(ref) {
			 var len = this.forbiddenPrefixs.length;
			 for(var i=0;i<len;i++) {
			 	var check = "document.getElementById(\"" + this.forbiddenPrefixs[i];
			 	isInternal = ref.indexOf(check) === 0;
			 	if(isInternal) break;
			 }
		}
		if(!isInternal)
	        this.handler.sendMessage(message);
	}

	//synchronized request/response from browser's DLC client to DLC server
	this.requestViaHttp = function(message) {
		var xmlHttpReq = this.getXmlHttpReq();
		xmlHttpReq.open("POST", this.dlcHttpBaseUrl, false);
		xmlHttpReq.send(message);
		return xmlHttpReq.responseText;
	}
	
	this.handler = null;
	this.isLoaded = false;
	this.hasConnected = false;
	this.windowWidth = 0;
	this.windowHeight = 0;
	
	//need to be called by window onload
	this.init = function() {
		if(navigator.appName.indexOf("Microsoft") != -1) {
			this.handler = DlcFlash;
		}else {
			this.handler = document.DlcFlash;
		}
		this.windowWidth = (window.innerWidth || document.body.offsetWidth);
		this.windowHeight = (window.innerHeight || document.body.offsetHeight);
	}
	
	//called by DLC flash when connection was established between browser and DLC server.
	this.connected = function() {
		this.hasConnected = true;
		this.loaded();
	}
	
	//called by DLC flash for normal message sent from DLC server
	this.eval = function(js) {
		eval(js);
	}

	//called by DLC flash for returning response to DLC server
	this.request = function(requestId, request) {
		var result = " ";
		try {
			result = eval(request);
			if(result) {
				result = result.toString();
			}
		}
		catch (err) {
			result = "ERROR:" + err.description;
		}
		this.respond(requestId, result);
	}
	
	//called by request method to return response to DLC server via DLC channel
	this.respond = function(requestId, result) {
		this.handler.respond(requestId, result);
	}
	
		
	//close the DLC flash connection to DLC server
	this.close = function() {
		this.handler.close();
	}
	
	this.loadPrefix = "";
	
	this.loaded = function() {
		if (!this.isLoaded) {
			return;
		}

	    var sessionId = this.getCookieValue("DLC_SID");
		if (sessionId == null) {
			sessionId = "" + new Date().getTime();
			this.setCookie("DLC_SID", sessionId);
		}
	    var requestId = this.getCookieValue("DLC_QID");
		if (requestId == null) {
			requestId = "" + new Date().getTime();
			this.setCookie("DLC_QID", requestId);
		}
		var message = this.loadPrefix + "load:[" + sessionId + "][" + requestId + "][" + location.href + "]";
		var historyChange = this.getCookieValue("DLC_HIS");
		if (historyChange == "true") {
			this.setCookie("DLC_HIS", "false");
			message += "[true]";
		}
		else {
			message += "[false]";
		}
		this.sendMessage(message);
	}
	
	this.getCookieValue = function(cookieName) {
		var results = document.cookie.match ( '(^|;) ?' + cookieName + '=([^;]*)(;|$)');
		if (results) {
			return decodeURI(results[2]);
		}
		return null;
	}

	this.setCookie = function(name, value, path, domain) {
		var cookieString = name + "=" + encodeURI(value);
  		if (path) {
			cookieString += "; path=" + encodeURI(path);
		}
		if ( domain ) {
			cookieString += "; domain=" + encodeURI(domain);
		}
		document.cookie = cookieString;
	}
	
	this.bootstrap = function() {
		this.createFlash(
			"src", this.dlcHttpBaseUrl + "SWF",
			"FlashVars", "DLC_HOST="+this.dlcHost+"&DLC_PORT="+this.dlcPort+"",
			"width", "0",
			"height", "0",
			"id", "DlcFlash",
			"name", "DlcFlash",
			"allowScriptAccess","always",
			"type", "application/x-shockwave-flash",
			"pluginspage", "http://www.adobe.com/go/getflashplayer"
		);
	}

	this.createFlash = function() {
		var ret = this.getArgs(arguments,
			"movie", 
			"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000",
			"application/x-shockwave-flash");
		this.generateObj(ret.objAttrs, ret.params, ret.embedAttrs);
	}
	
	this.generateObj = function(objAttrs, params, embedAttrs) {
		var str = '';
		var isIE  = (navigator.appVersion.indexOf("MSIE") != -1) ? true : false;
		var isWin = (navigator.appVersion.toLowerCase().indexOf("win") != -1) ? true : false;
		var isOpera = (navigator.userAgent.indexOf("Opera") != -1) ? true : false;
		if (isIE && isWin && !isOpera) {
  			str += '<object ';
  			for (var i in objAttrs)
  				str += i + '="' + objAttrs[i] + '" ';
  			str += '>';
  			for (var i in params)
  				str += '<param name="' + i + '" value="' + params[i] + '" /> ';
  			str += '</object>';
		} else {
  			str += '<embed ';
  			for (var i in embedAttrs)
  				str += i + '="' + embedAttrs[i] + '" ';
  			str += '></embed>';
		}
		var div = document.getElementById('DLC_OBJ');
  		div.innerHTML=str;
		DLC.init();
	}

	this.getArgs = function(args, srcParamName, classid, mimeType) {
		var ret = new Object();
		ret.embedAttrs = new Object();
		ret.params = new Object();
		ret.objAttrs = new Object();
		for (var i=0; i < args.length; i=i+2) {
			var currArg = args[i].toLowerCase();    

			switch (currArg){	
				case "classid":
					break;
				case "pluginspage":
					ret.embedAttrs[args[i]] = args[i+1];
					break;
				case "src":
				case "movie":	
					ret.embedAttrs["src"] = args[i+1];
					ret.params[srcParamName] = args[i+1];
					break;
				case "type":
				case "codebase":
					ret.objAttrs[args[i]] = args[i+1];
					break;
				case "id":
				case "width":
				case "height":
				case "class":
				case "title":
				case "name":
					ret.embedAttrs[args[i]] = ret.objAttrs[args[i]] = args[i+1];
					break;
				default:
					ret.embedAttrs[args[i]] = ret.params[args[i]] = args[i+1];
			}
		}
		ret.objAttrs["classid"] = classid;
		if (mimeType) ret.embedAttrs["type"] = mimeType;
		return ret;
	}
	
	this.getXmlHttpReq = function() {
		var xmlHtmlReq = false;
		try {
			xmlHtmlReq = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				xmlHtmlReq = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				xmlHtmlReq = false;
			}
		}
		if (!xmlHtmlReq && typeof XMLHttpRequest!='undefined') {
			xmlHtmlReq = new XMLHttpRequest();
		}
		return xmlHtmlReq;
	}

	this.enablers = new Array();
	this.addEnabler = function(enabler) {
		this.enablers[this.enablers.length]=enabler;
	}

	this.disablers = new Array();
	this.addDisabler =  function(disabler) {
		this.disablers[this.disablers.length]=disabler;
	}
}

//onload
window.onloadListeners=new Array();
window.addOnLoadListener = function (listener) {
	window.onloadListeners[window.onloadListeners.length]=listener;
}
window.onload = function() {
	for(var i=0;i<window.onloadListeners.length;i++){
		func = window.onloadListeners[i];
		func.call();
	}
}
window.addOnLoadListener(enableDLCEvents);
function enableDLCEvents() {
	DLC.bootstrap();
	DLC.init();

	//enable DLC client if there are some
	var enablers = DLC.enablers, len = enablers.length;
	for(var i=0;i<len;i++) {
		var enabler = enablers[i];
		if(typeof(enabler) == "function") {
			enabler();
		}
	}

	DLC.isLoaded = true;
	if (DLC.hasConnected) {
		DLC.finishLoading();
	}
}

window.onbeforeunload = function(evt){
	//disable DLC client if there are some
	var disablers = DLC.disablers, len = disablers.length;
	for(var i=0;i<len;i++) {
		var disabler = disablers[i];
		if(typeof(disabler) == "function") {
			disabler();
		}
	}
	DLC.close();
}
