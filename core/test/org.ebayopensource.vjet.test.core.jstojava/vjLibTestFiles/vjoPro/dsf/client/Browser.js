vjo.ctype("vjoPro.dsf.client.Browser")
.props({
	bFirefox: false,//<public boolean
	bWebTV: false,//<public boolean
	bOpera: false,//<public boolean
	bNav: false,//<public boolean
	bIE: false,//<public boolean
	bSafari: false,//<public boolean
	bWin: false,//<public boolean
	bMac: false,//<public boolean
	bMacppc: false,//<public boolean
	bMactel: false,//<public boolean
	bActiveXSupported: false,//<public boolean
	bWinXp: false,//<public boolean
	bXpSp2: false,//<public boolean
	bAOL: false,//<public boolean
	bVista: false,//<public boolean
	iVer: -1,//<public Number
	fVer: -1,//<public Number
	fMinorVer: -1,//<public Number
	aMimeTypes: null,
	init : function() {
		
		var nv = navigator;
		var agt = nv.userAgent.toLowerCase();//<String 
		var i = 0, ver;//<Number
		
		with (this){
			if (agt.has("webtv"))
			{
			bWebTV = true;
			i = agt.indexOf("webtv/") + 6;
			}
			else if (agt.has("firefox"))
			{
			bFirefox = true;
			i = agt.lastIndexOf("firefox") + 8;
			}
			else if (agt.has("safari"))
			{
			bSafari = true;
			i = agt.lastIndexOf("safari") + 7;
			}
			else if(typeof(window.opera)!="undefined")
			{
			bOpera = true;
			i = agt.lastIndexOf("opera") + 6;
			}
			else if (nv.appName.is("Netscape"))
			{
			bNav = true;
			i = agt.lastIndexOf("/") + 1;
			}
			else if (agt.has("msie"))
			{
			bIE = true;
			i = agt.indexOf("msie") + 4;
			if (agt.has('aol') || agt.has('america online'))
			bAOL = true;
			}
			ver = bOpera?window.opera.version():agt.substring(i);
			//ver = agt.substring(i);
			iVer = parseInt(ver);
			fVer = parseFloat(ver);
			fMinorVer = fVer - iVer;
			
			//Operating system detection
			bWin = agt.has("win");
			bWinXp = (bWin && agt.has("windows nt 5.1"));
			bVista = (bWin && agt.has("windows nt 6.0"));
			bXpSp2 = (bWinXp && agt.has("sv1"));
			bMac = agt.has("mac");
			bMacppc = (bMac && agt.hasAny("ppc","powerpc"));
			bMactel = (bMac && agt.has("intel"));
			
			aMimeTypes = nv.mimeTypes;
			
			//ActiveX support
			bActiveXSupported =	(!(bMac || bMacppc) && (typeof(ActiveXObject) == 'function'));
		}
	}
})
.inits(function () {
vjoPro.dsf.client.Browser.init();
})
.endType();
