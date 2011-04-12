vjo.ctype('vjoPro.samples.basic.eventhandler.EventHandlerEx5') //< public
.needs('vjoPro.dsf.document.Element')
.protos({

/**
* @return void
* @access public
* @param {String} pDivId
* @param {int} pInitCount
*
*/
//> public constructs(String pDivId,int pInitCount)
constructs:function(pDivId,pInitCount){
this.count = pInitCount;
this.divId = pDivId;
},

/**
* @return int
* @access public
* @JsEventHandler
*
*/
//> public int onInterval()
onInterval:function(){
vjoPro.dsf.document.Element.get(this.divId).innerHTML = this.count++;
}

})
.endType();
