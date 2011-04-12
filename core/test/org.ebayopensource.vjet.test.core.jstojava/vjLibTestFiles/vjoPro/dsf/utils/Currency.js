vjo.ctype("vjoPro.dsf.utils.Currency")
/**
* Utility class for currency operations.
*/
.props({
/**
* Converts a currency string value to float type.
*
* @param {String} value
*        a String value of the currency
* @param {boolean} nonDecimal
*        <code>true</code> if there is no decimal in the value
* @return {float}
*        the float type of the value
*/
//> public float parse(String, boolean)
parse : function(psVal,pbNonDecimal) {
var v = new String(psVal);
if (pbNonDecimal)
{
//Remove periods and replace comma with period
v = v.replace(/\./g,"");
v = v.replace(/,/g,".");
}

//Remove any commas
v = v.replace(/,/g,"");
if (isNaN(v))
return v;

v = parseFloat(v);
return v;
},

/**
* Rounds the currency value with specified number of the decimal.
*
* @param {String} val
* 		 a String value of the currency
* @param {int} NoofDec
*        number of the decifmal
* @return {float}
*        the rounded currency value
*/
//> public float round(String, int);
round : function(psVal,pNoofDec) {
pNoofDec = pNoofDec || 2;
var r = Math.pow(10, pNoofDec);
return Math.round(psVal*r)/r;
},

/**
* Formats the currency value. If it is VAT-Exempt, six digitals decimal
* will be added. Otherwise, the number of the decimal will be two.
*
* @param {String} val
* 		 a String value of the currency
* @param {boolean} nonDecimal
*        <code>true</code> if there is no decimal in the value
* @param {boolean} VATExempt
*        <code>true</code> if it is VAT-Exempt
* @param {String}
*        the formatted currency value
*/
//> public String format(String, boolean, boolean);
format : function(psVal,pbNonDecimal,pVATExempt) {
var iDec = (pVATExempt)?6:2;
var v = new String(this.round(psVal, iDec)), dInd = v.lastIndexOf(".");
if (dInd == -1)
{
//No decimal, add to end
v += pVATExempt?".000000":".00";
}
else
{
var iL = v.length - dInd - 1;
if (iL < iDec)
{
for (var i=0;i<iDec-iL;i++)
v += "0";
}
}
//Replace decimal with comma for countries that require it
if (pbNonDecimal)
{
//Replace all commas with periods
v = v.replace(",",".");

//Replace the last decimal with a comma
dInd = v.lastIndexOf(".");
v = v.substring(0,dInd) + "," + v.substr(dInd+1);
}
return v;
},

/**
* Detects whether the value is in the decimal format.
*
* @param {String} val
*        a value to be detectes
* @return {boolean}
*        <code>true</code> if the value is in the decimal format
*/
//>public boolean isDecimalFormat(String);
isDecimalFormat : function(psVal) {
var sReg = "^[0-9,]*[.]{1}[0-9]{1,2}";
var oRegex = new RegExp(sReg,"g"); //<<
return oRegex.test(psVal);
},

/**
* Converts the currency value to int'l format.
* <p>
* For examples:
* <li>1,000.00 becomes 1.000,00
* <li>1000.00 becomes 1000,00
* <li>1000 remains 1000
*
* @param {String} val
*        a value to be detectes
* @param {String}
*        the currency value with int'l format
*/
//> public String toIntlFormat(String);
toIntlFormat : function(psVal) {
//converts to intl format

var sNewVal = psVal.replace(new RegExp(",","g"), "_");
sNewVal = sNewVal.replace(new RegExp("[.]","g"), ",");
sNewVal = sNewVal.replace(new RegExp("_","g"), ".");
return sNewVal;
}
})
.endType();

