vjo.ctype("vjoPro.dsf.utils.Bit")
.needs("vjoPro.dsf.cookie.VjCookieJar")
.props({
CJ : vjoPro.dsf.cookie.VjCookieJar,
/**
* Gets the multi-bit value from a particular position given a number of bits.
*
* @param {String} dec
*        A bit string contains series of flags
* @param {int} pos
*        Flag position in the bit string
* @param {int} bit
*        The bit length of the flag
* @return {int}
*        The flag value in binary format
*/
//> public int getMulti(String,int,int);
getMulti: function(piDec, piPos, piBits) {
var r = "",i,CJ=this.CJ;
for(i=0;i<piBits;i++){
r = CJ.getBitFlag(piDec,piPos+i) + r ;
}
return parseInt(r,2);
},

/**
* Sets the multi-bit flag at particular position given a number of bits
*
* @param {String} dec
*        A bit string contains series of flags
* @param {int} pos
*        Flag position in the bit string
* @param {int} bit
*        The bit length of the flag
* @param {int} value
*        The value of the flag in decimal format
* @return {int}
*        The new bits string
*/
//> public int setMulti(String,int,int,Number);
setMulti: function(piDec, piPos, piBits, piVal) {
var i=0,CJ=this.CJ, v, l, e, j;
//convert to binary and take piBits out of it
v = piVal.toString(2).substring(0,piBits);
l = v.length;
if(l<piBits){
e = piBits-l;
for(j=0;j<e;j++)
{
v = "0"+v;
}
l = l+e;
}
for(i=0;i<l;i++){
piDec = CJ.setBitFlag(piDec,piPos+i,v.substring(l-i-1,l-i));
}
return piDec;
}
}).endType();
