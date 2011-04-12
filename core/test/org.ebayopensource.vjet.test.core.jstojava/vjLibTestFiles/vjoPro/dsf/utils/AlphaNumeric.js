vjo.ctype("vjoPro.dsf.utils.AlphaNumeric")
.needs(["vjoPro.dsf.typeextensions.string.Comparison",
"vjoPro.dsf.typeextensions.number.DecimalToHex"])
.props({
sUpperChars : 'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
sLowerChars : 'abcdefghijklmnopqrstuvwxyz',
sNumbers : '0123456789',

//Local methods

/**
* Detects whether the given string is in upper case.
*
* @param {String} str
*        a string to be checked
*/
//> public boolean isUpper(String);
isUpper : function(psStr) {
return this.doCompare(psStr,this.sUpperChars);
},

/**
* Detects whether the given string is in lower case.
*
* @param {String} str
*        a string to be checked
*/
//> public boolean isLower(String);
isLower : function(psStr) {
return this.doCompare(psStr,this.sLowerChars);
},

/**
* Detects whether given string is in the alphabet.
*
* @param {String} str
*        a string to be checked
*/
//> public boolean isAlpha(String);
isAlpha : function(psStr) {
with (this){
return doCompare(psStr,sUpperChars + sLowerChars);
}
},

/**
* Detects whether the given string is a number
*
* @param {String} str
*        a string to be checked
*/
//> public boolean isNumeric(String);
isNumeric : function(psStr) {
return this.doCompare(psStr,this.sNumbers);
},

/**
* Detects whether the given string is a number or in the alphabet.
*
* @param {String} str
*        a string to be checked
*/
//> public boolean isAlphaNumeric(String,String);
isAlphaNumeric : function(psStr,pExceptions) {
var ex = pExceptions || "";
with (this)
return doCompare(psStr,sUpperChars + sLowerChars + sNumbers + ex);
},

//> private boolean doCompare(String,String);
doCompare : function(psStr,pChars) {
var len = psStr.length, rv = true;
for (var i=0; i<len && rv; i++)
rv = pChars.has(psStr.charAt(i));
return rv;
}
})
.endType();


