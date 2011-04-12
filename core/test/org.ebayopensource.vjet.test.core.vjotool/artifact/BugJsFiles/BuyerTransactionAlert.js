vjo.ctype("BugJsFiles.BuyerTransactionAlert")
.needs(["vjo.dsf.cookie.VjCookieJar",
		"vjo.dsf.typeextensions.string.Comparison",
		"vjo.dsf.client.Browser",
		"vjo.dsf.Element",
		"vjo.darwin.core.ebaytoolbar.VjEbayToolbarDetect"])
.protos({
	/**
	 * @return void
	 * @access public
	 * @param {String} pId
	 * @param {int} iPollingInterval
	 * @param {int} iMaxHits
	 * @param {int} iHitTimeout
	 * @param {String} pServerUrl
	 * @param {String} pImgServer
	 * @param {String} pViewItemUrl
	 * @param {String} pEndingSoonMsg
	 * @param {String} pOutbidMsg
	 * @param {String} pSecondChanceMsg
	 * @param {String} pTransConfirmMsg
	 */
	 //> public constructs(String pId,int iPollingInterval,int iMaxHits,int iHitTimeout,String pServerUrl,String pImgServer,String pViewItemUrl,String pEndingSoonMsg,String pOutbidMsg,String pSecondChanceMsg,String pTransConfirmMsg)
	constructs : function(pId,iPollingInterval,iMaxHits,
		iHitTimeout,pServerUrl,pImgServer,
		pViewItemUrl,pEndingSoonMsg,
		pOutbidMsg,pSecondChanceMsg,
		pTransConfirmMsg)
		{
			this.sId = pId;vj
		
			this.iPollingInterval = iPollingInterval;
			this.iMaxHits = iMaxHits;
			this.iHitTimeout = iHitTimeout;
			this.iServerHits = 0;//this is the number of hits 2 server which is supposed to drop the cookie
			this.sLastCookieletValue = '';//stores the last value of a2p cookielet.
			this.sServerUrl = pServerUrl;
			if(document.location.protocol.has("https"))
			{	pImgServer = pImgServer.replace('http://pics.','https://securepics.');}
			this.sImgServer = pImgServer+'/';
			this.sViewItemUrl = pViewItemUrl;
		
			this.aAlertInfo = [ ["h:h:alt:2",pOutbidMsg,"icon/iconOutbid_16x16.gif"],
									    ["h:h:alt:3",pEndingSoonMsg,"icon/iconWatchB_16x16.gif"],
										["h:h:alt:4",pOutbidMsg,"icon/iconOutbid_16x16.gif"],
										["h:h:alt:5",pSecondChanceMsg,"icon/iconchanceBlu_16x16.gif"],
										["h:h:alt:tcr",pTransConfirmMsg,"icon/iconMailBlue_16x16.gif"]
									  ];
		
		
		
			var c,oC = vjo.dsf.client.Browser, oCJ = vjo.dsf.cookie.VjCookieJar;
		
			//check for certain browsers, disable BTA for browser versions < IE5, < Opera 7.5 and < NS7.X
			if((oC.bNav && oC.iVer < 7) || (oC.bOpera && (oC.iVer + oC.fMinorVer) < 0.5) || (oC.bIE && oC.iVer < 5)) {return;}
		
			//Check for "ebaysignin" cookie. If no, return.
			//var oCJ = oD.oCookieJar;
			c = oCJ.readCookie("ebaysignin");
			if(!c || !c.is('in')) {return;}
		
			//Check if Message Center cookie exists. If yes, return.
			c = oCJ.readCookie("dp1","a1p");
			if(c && c.length > 0 && parseInt(c) > 0) {return;}
		
			//Check if toolbar is enabled. If yes, return.
			if(vjo.darwin.core.ebaytoolbar.VjEbayToolbarDetect.isEnabled()) {return;}
	},

	setValue : function(pVal,pIsText) {
		var oL = this.oL;
		if(oL)
		{
			// we can set it to empty if it previously set it
			if (pVal.is('') && !oL.ctrld) {return;}
			if (pIsText) 
			{
				if (vjo.dsf.client.Browser.bFirefox) 
					{oL.textContent = pVal;}
				else 
					{oL.innerText = pVal;}
			}
			else 
				{oL.innerHTML = pVal;}
			oL.ctrld = 1;
		}
	},
	//Local functions
	//Event handler of onrefresh event
	/**
	 * @return void
	 * @access public
	 * @JsEventHandler
	 
	 */
	 //> public void onRefresh()
	onRefresh : function ()
	{
		var E = vjo.dsf.Element;
		if (!this.oL) {
			this.oL = E.get(this.sId);
		}
		//Check Bta PC 722. If(false) then return;
		//if(!_GH_Bta_PC) {return;}
		if(!this.oL) {return;}
	
		//ALL THE REFRESH LOGIC WILL BE DONE HERE
		//Check for rebate cookie. If (present) then return
		var c = vjo.dsf.cookie.VjCookieJar.readCookie('npii','mri');
		if(c)// if rebate cookie exist
		{
			return;
		}
		//Check for transaction alert cookie. If (not present) or (present and expired) fire oncookieexpire event
		c = vjo.dsf.cookie.VjCookieJar.readCookie("ebay","a2p");
	
	// REMOVE THIS AFTER TESTING
	//c = "00000060299992345678.";
	
		if(!c)//if a2p cookie does not exist
		{
			this.onCookieExpire();
			return;
		}
	
		//Parse the cookie
		var at = parseInt(c.charAt(8));//at : Alert Type
		if(isNaN(at)) {return;}
	
		if(at === 0)//PC is turned OFF
		{
			this.setValue('');
			return;
		}
	
		var nrt = parseInt(c.substring(0,8),16) * 1000;//nrt : Next Refresh Time in milliseconds
		if(isNaN(nrt)) {return;}
	
		var ct = new Date();
		ct = ct.getTime();//ct : Current Time in milliseconds
		
	// REMOVED FTER DEBUG
	//nrt = ct+1;
		if(at == 6 || at == 9)
		{
			//reset the server hits counter to zero
			if(!c.is(this.sLastCookieletValue))
				{this.iServerHits = 0;}
			//since no alerts are there for the user remove the alerts if any are shown already.
			this.setValue('');
			this.sLastCookieletValue = c;
			//fire event after nrt seconds
			var t = (nrt > ct) ? parseInt((nrt - ct) / 1000) : this.iPollingInterval;
			window.setTimeout(vjo.hitch(this,this.onCookieExpire),t * 1000);
			return;
		}
		
		if(ct >= nrt)//hit the server to make it drop the cookie
		{
			this.onCookieExpire();
			return;
		}
		//ELSE WRITE THE ALERT	
		//reset the server hits counter to zero
		this.iServerHits = 0;
	
		var cfg = this.aAlertInfo;
		//Check whether alert type is valid
		if(at < 0 && at >= cfg.length) {return;}
	
		var ii = c.substring(9,c.lastIndexOf("."));//ii : Item Id
	
		//else set the innerHTML according to alert type
		if(!c.is(this.sLastCookieletValue))
		{
			var alertInfo = cfg[at-1],imgSrv=this.sImgServer;
		
			
			var sSpacer = imgSrv + "s.gif";
			var sHTML = '<img src="' + sSpacer + '" width="10" height="16" style="vertical-align:middle">|<img src="' + sSpacer + '" width="10" height="16" style="vertical-align:middle">';
			sHTML += '<img src="' + imgSrv + alertInfo[2] + '?t" style="vertical-align:middle"><img src="' + sSpacer + '" width="5" height="16" style="vertical-align:middle">';
			
			var url = this.sViewItemUrl + "&item=" + ii;
			
			sHTML += '<a href="' + url + '&ssPageName=' + alertInfo[0] + '">' + alertInfo[1] + '</a>';
			this.setValue(sHTML);
			this.sLastCookieletValue = c;
		}
	
		//fire event periodically
		this.fireRefreshEvent();
	},

	//This function can be used to fire the refresh event after a particular time interval
	fireRefreshEvent : function (pInterval) {
		if(!pInterval) {pInterval = this.iPollingInterval;}
		//fire event periodically
		window.setTimeout(vjo.hitch(this,this.onRefresh),pInterval * 1000);
	},

	//Event handler of oncookieexpire event
	onCookieExpire : function ()
	{
		var oCJ = vjo.dsf.cookie.VjCookieJar, signin = oCJ.readCookie("ebaysignin");
		//checking ebaysignin cookie value before making server request
		if(!signin.has("in")) {return;}
		
		//Check for protocol, if it is https: then return
		if(document.location.href.has("https:")) {return;}
	
		//code to hit the server so that cookie is dropped.
		//Server URL is hit by setting the src of an IMG tag or IFRAME tag.
		//After cookie is dropped, fire onrefresh event.
	
		if(this.iServerHits < this.iMaxHits)
		{
			this.iServerHits++;
			var ct = new Date();//get the current date and time
			ct = ct.getTime();//ct : Current Time in milliseconds
			this.setValue('<img height="1" width="1" src="' + this.sServerUrl + '&clientTime=' + ct + '" style="visibility:hidden;vertical-align:middle">');
			//fire refresh event after some n seconds. This has to be decided by ebay JS team
			this.fireRefreshEvent(this.iHitTimeout);
		}
		else
		{
			//first clear the alerts
			this.setValue('');
			//write ebay.a2p cookielet
			oCJ.writeCookielet("ebay","a2p","1111111101111111111.");
		}
	}
})
.endType();




