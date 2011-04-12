vjo.ctype("vjoPro.dsf.utils.URL")
.props({
/**
* Adds argument to the URL string. If there are already arguments following
* the url, the newly added argument will overwrite all of them.
*
* @param {String} url
*        An url string the argument will add to
* @param {String} argName
*        A string name of the new added argument
* @param {String} argValue
*        A string value of the new added argument
* @return {String}
*        An url string with new added argument
*/
//> public String addArg(String,String,String);
addArg : function(psUrl, psArgName, psArgValue) {
if (psUrl == null || psUrl == undefined) {
return null;
}

if (psUrl.indexOf("?") < 0) {
psUrl += "?" + psArgName + "=" + psArgValue;
return psUrl;
}

var argPair  = this.getArgPairIfExists(psUrl, psArgName);
if (argPair !== null) {
psUrl = psUrl.replace(argPair, psArgName + "=" + psArgValue);
} else {
psUrl += "&" + psArgName + "=" + psArgValue;
}
return psUrl;
},

/**
* Gets the value of specified argument name from a given url.
*
* @param {String} url
*        An url string contains the argument pair
* @param {String} argName
*        A string name of the argument
* @return {String}
*        The value of the specified argument
*/
//> public String getArg(String,String);
getArg : function(psUrl, psArgName) {
if (psUrl == null || psUrl == undefined) {
return null;
}

if (psUrl.indexOf("?") < 0) {
return null;
}

var argPair  = this.getArgPairIfExists(psUrl, psArgName);//<String
if (argPair !== null) {
return argPair.substring(argPair.indexOf("=")+1);
}
return null;
},

//>  private String getArgPairIfExists(String,String);
getArgPairIfExists : function(psUrl, psArgName) {
var argsIndex = psUrl.indexOf("?");
if (argsIndex < 0) {
return null;
}

var argsStr = psUrl;
var argPair, argName;
while (argsIndex >= 0) {
argsStr = argsStr.substring(argsIndex+1);
argPair = argsStr;
argsIndex = argsStr.indexOf("&");
if (argsIndex >= 0) {
argPair = argsStr.substring(0, argsIndex);
}
argName = argPair.substring(0, argPair.indexOf("="));
if (argName == psArgName) {
//parameter exists
return argPair;
}
}
return null;
}
})
.endType();

