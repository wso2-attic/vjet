vjo.ctype("vjoPro.dsf.client.ActiveX")
.needs("vjoPro.dsf.client.Browser")
.props({
init : function(){
var oC = vjoPro.dsf.client.Browser;
if (oC.bIE){
var d = document;//<HTMLDocument
var dw = function(s){d.writeln(s);}
dw(  '<scr' + 'ipt language="vbscript" type="text/vbscript">' );
dw( 	'	Function vbCheckActiveXControl (pActXName)' );
dw(	'		aX = false');
dw( 	'		on error resume next' );
dw( 	'		aX = IsObject(CreateObject(pActXName))' );
dw(  '		vbCheckActiveXControl = aX');
dw(  'End Function');
dw( 	'</scr' + 'ipt>' );
}
},

/**
* Detects if the specified ActiveX control is loaded.
*
* @param {String} name
*        The name of the ActiveX control to be detected
* @return {boolean}
*        <code>true</code> if the control is loaded
*/
//>public boolean isLibLoaded(String);
isLibLoaded : function(psName){
var oC = vjoPro.dsf.client.Browser;//<Type::Browser
return oC.bActiveXSupported && vbCheckActiveXControl(psName);
}
})
.inits(function () {
vjoPro.dsf.client.ActiveX.init();
})
.endType();



