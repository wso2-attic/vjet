vjo.ctype("dsf.dom.html.level2.BaseTest")
.protos({

//> public void assertURIEquals(String scheme, String path, String host, String file, String name, String query, String fragment, String isAbsolute, String actual)
assertURIEquals:  function(scheme, path, host, file, name, query, fragment, isAbsolute, actual) {
//
//  URI must be non-null
var assertTrue = null;
var assertEquals = null;
assertTrue(actual != null);

var uri = actual;

var lastPound = actual.lastIndexOf("#");//<int
var actualFragment = "";
if(lastPound != -1) {
//
//   substring before pound
//
uri = actual.substring(0,lastPound);
actualFragment = actual.substring(lastPound+1);
}
if(fragment != null) assertEquals(fragment, actualFragment);

var lastQuestion = uri.lastIndexOf("?");//<Number
var actualQuery = "";//<String
if(lastQuestion != -1) {
//
//   substring before pound
//
uri = actual.substring(0,lastQuestion);
actualQuery = actual.substring(lastQuestion+1);
}
if(query != null) assertEquals(query, actualQuery);

var firstColon = uri.indexOf(":");
var firstSlash = uri.indexOf("/");
var actualPath = uri;
var actualScheme = "";
if(firstColon != -1 && firstColon < firstSlash) {
actualScheme = uri.substring(0,firstColon);
actualPath = uri.substring(firstColon + 1);
}

if(scheme != null) {
assertEquals(scheme, actualScheme);
}

if(path != null) {
assertEquals(path, actualPath);
}

if(host != null) {
var actualHost = "";//<String
if(actualPath.substring(0,2) == "//") {
var termSlash = actualPath.substring(2).indexOf("/") + 2;//<Number
actualHost = actualPath.substring(0,termSlash);
}
assertEquals(host, actualHost);
}

if(file != null || name != null) {
var actualFile = actualPath;//<String
var finalSlash = actualPath.lastIndexOf("/");//<Number
if(finalSlash != -1) {
actualFile = actualPath.substring(finalSlash+1);
}
if (file != null) {
assertEquals(file, actualFile);
}
if (name != null) {
var actualName = actualFile;
var finalDot = actualFile.lastIndexOf(".");
if (finalDot != -1) {
actualName = actualName.substring(0, finalDot);
}
assertEquals(name, actualName);
}
}

if(isAbsolute != null) {
assertEquals(isAbsolute, actualPath.substring(0,1) == "/");
}
}

})
.endType();

