vjo.ctype("vjoPro.dsf.service.DefaultDedupComparable")
.needs("vjoPro.dsf.common.IDedupComparable")
.satisfies("vjoPro.dsf.common.IDedupComparable")
.protos({

//> public void constructs();
constructs : function () {
},
//> public boolean shouldTrack(Object);
shouldTrack : function (pMessage) {
return true;
},

//> public boolean isDedup(Object,Object);
isDedup : function (pCurrentMessage, pMessage) {
var currentServiceId = pCurrentMessage.svcId;
var serviceId = pMessage.svcId;
if (currentServiceId === serviceId)
return true;
else
return false;
}
})
.endType();
