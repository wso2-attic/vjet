package {
	import flash.display.Sprite;
	import flash.events.DataEvent;
	import flash.events.Event;
	import flash.external.ExternalInterface;
	import flash.net.XMLSocket;
	import flash.system.Security;

	public class DSFLiveConnect extends Sprite
	{
		private var xmlSocket:XMLSocket = new XMLSocket();
		private static var DLC_CONNECTED:String = "DLC_CONNECTED";		
		private static var DLC_EXIT:String = "DLC_EXIT";
		private static var DLC_REQUEST:String = "DLC_REQUEST:";
		private static var DLC_RESPONSE:String = "DLC_RESPONSE:";
		
		private static var JS_DLC_eval:String = "DLC.eval";
		private static var JS_DLC_request:String = "DLC.request";
		private static var JS_DLC_connected:String = "DLC.connected";
		
		public function DSFLiveConnect()
		{
			Security.allowDomain("*");
			ExternalInterface.addCallback("sendMessage", sendMessage);
			ExternalInterface.addCallback("respond", respond);
			ExternalInterface.addCallback("close", close);
									
			xmlSocket.addEventListener(DataEvent.DATA, onData);
			xmlSocket.addEventListener(Event.CONNECT, onConnect);
			
			var policyHost:String = "xmlsocket://" + loaderInfo.parameters.DLC_HOST 
				+ ":" + loaderInfo.parameters.DLC_PORT;
			Security.loadPolicyFile(policyHost);

			xmlSocket.connect(loaderInfo.parameters.DLC_HOST, Number(loaderInfo.parameters.DLC_PORT));		
		}
		
		
		public function sendMessage(message:String) : void {			
			xmlSocket.send(message);
		}
		
		public function respond(requestId:String, message:String) : void {
			message = DLC_RESPONSE + requestId + ":" + message;
			xmlSocket.send(message);
		}
		
		public function close() : void {
			xmlSocket.close();
		}
		
		private function callJs(funcName:String, ...parameters) : *{
			parameters.unshift(funcName);
			return ExternalInterface.call.apply(this, parameters);
		}
		
		private function onData(event:DataEvent):void {
			if (event.data == DLC_EXIT) {
				close();
				callJs(JS_DLC_eval, "window.name='DLC_WIN'; origWin=window.open('','DLC_WIN'); window.opener=top; window.close();");
			} else if (event.data.indexOf(DLC_REQUEST) == 0) {
				var index:int = event.data.indexOf(":", DLC_REQUEST.length);
				var resquestId:String = event.data.substring(DLC_REQUEST.length, index);
				var msg:String = event.data.substring(index + 1);
				callJs(JS_DLC_request, resquestId, msg);
			}			
			else {
    			callJs(JS_DLC_eval, event.data);
   			}
		}
		
		private function onConnect(event:Event):void {
			sendMessage(DLC_CONNECTED);
			callJs(JS_DLC_connected, null);
		}	
	}
}
