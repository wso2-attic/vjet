var obj = swfobject.getObjectById("myId");
if (obj) {
  obj.doSomething(); // e.g. an external interface call
}

var playerVersion = swfobject.getFlashPlayerVersion(); // returns a JavaScript object
var majorVersion = playerVersion.major; // access the major, minor and release version numbers via their respective properties

if (swfobject.hasFlashPlayerVersion("9.0.18")) {
  // has Flash
}
else {
  // no Flash
}

function sayHi() {
  alert("Hi!");
}
swfobject.addLoadEvent(sayHi);

function sayHi() {
  alert("Hi!");
}
swfobject.addDomLoadEvent(sayHi);

   if (swfobject.hasFlashPlayerVersion("6.0.0")) {
      var fn = function() {
        var att = { data:"test.swf", width:"780", height:"400" };
        var par = { flashvars:"foo=bar" };
        var id = "replaceMe";
        var myObject = swfobject.createSWF(att, par, id);
      };
      swfobject.addDomLoadEvent(fn);
    }
   
    
	
	var flashvars = {};
if (swfobject.getQueryParamValue("foo") && swfobject.getQueryParamValue("abc")) {
  flashvars.foo = swfobject.getQueryParamValue("foo");
  flashvars.abc = swfobject.getQueryParamValue("abc");
}
var params = {};
var attributes = {};
swfobject.embedSWF("myContent.swf", "altContent", "100%", "100%", "9.0.0","expressInstall.swf", flashvars, params, attributes);

swfobject.switchOffAutoHideShow();
swfobject.embedSWF("test.swf", "myContent", "300", "120", "9", "expressInstall.swf");

function cancelFunction() {
        alert("Express Install was cancelled");
}
if (swfobject.hasFlashPlayerVersion("10")) {
        var fn = function() {
                var att = { data:"flashContent.swf", width:"300", height:"120" };
                var par = {};
                var id = "myContent";
                swfobject.createSWF(att, par, id);
        };
        
}
else {
        var fn = function() {
                var att = { data:"expressInstall.swf", width:"600", height:"240" };
                var par = {};
                var id = "myContent";
                swfobject.showExpressInstall(att, par, id, cancelFunction);
        }
}
swfobject.addDomLoadEvent(fn);


function test() {
    var output = "swfobject.ua.w3 = " + swfobject.ua.w3;
    output += "\nswfobject.ua.pv[0] (major version) = " + swfobject.ua.pv[0];
    output += "\nswfobject.ua.pv[1] (minor version) = " + swfobject.ua.pv[1];
    output += "\nswfobject.ua.pv[2] (release version) = " + swfobject.ua.pv[2];
    output += "\nswfobject.ua.wk = " + swfobject.ua.wk;
    output += "\nswfobject.ua.ie = " + swfobject.ua.ie;
    output += "\nswfobject.ua.win = " + swfobject.ua.win;
    output += "\nswfobject.ua.mac = " + swfobject.ua.mac;
    alert(output);
}
swfobject.addLoadEvent(test);

