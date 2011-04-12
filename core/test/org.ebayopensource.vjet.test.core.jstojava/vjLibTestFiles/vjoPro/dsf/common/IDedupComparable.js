vjo.itype('vjoPro.dsf.common.IDedupComparable')
.protos({
/**
* Checks whether the given message is allowed to be duplicate.
*
* @param {vjoPro.dsf.resource.html.event.handler.JsHandlerObjectEnum} message
*        a message to be checked
* @return {boolean}
*        <code>true</code> if this message is not allowed to be duplicate
*/
//> public boolean shouldTrack(Object);
shouldTrack : vjo.NEEDS_IMPL,

/**
* Checks whether the two messages are duplicate.
*
* @param {vjoPro.dsf.resource.html.event.handler.JsHandlerObjectEnum} currentDsfMsg
*        the current message to be compared
* @param {vjoPro.dsf.resource.html.event.handler.JsHandlerObjectEnum} dsfMsg
*        Another message to be compared from
* @return {boolean}
*        <code>true</code> if two messages are duplicate
*/
//> public boolean isDedup(Object, Object);
isDedup : vjo.NEEDS_IMPL
})
.endType();
