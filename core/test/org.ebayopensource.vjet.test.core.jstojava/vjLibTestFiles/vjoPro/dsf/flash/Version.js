vjo.ctype("vjoPro.dsf.flash.Version")
.needs(["vjoPro.dsf.client.Browser","vjoPro.dsf.client.ActiveX","vjoPro.dsf.utils.Bit","vjoPro.dsf.cookie.VjCookieJar"])
.props({
//>public int[]
versions : [10,9], //versions to check
//> public int get();
get: function () {
var t = this; //<type::Version
var B = this.vj$.Browser; //<type::Browser
var v=0, vs = this.versions, i, A=t.vj$.ActiveX, cv;
//read from ebay.sbf cookielet
cv = t.rw(false,v);
if(cv)
return (cv==1)?0:cv; //returning zero incase of cookievalue 1

if (B.bIE && B.bWin && !B.bOpera)
{
for (i=0; i < vs.length; i++)
{
if(A.isLibLoaded("ShockwaveFlash.ShockwaveFlash."+vs[i])){
v = vs[i];
break;
}
}
}
else
{
var n = navigator, pd, id, swf = "Shockwave Flash";
if (n.plugins[swf])
{
pd = n.plugins[swf].description;
id = pd.indexOf("Flash")+5;
v = parseInt(pd.substr(id,pd.length));
}
if(B.bWebTV)
v = 3;
}
//update ebay.sbf cookielet
t.rw(true,v);
return v;
},
rw:function(write,value){
var t = this, n = t.vj$, C = n.VjCookieJar, B = n.Bit;
//40,41,42,43,44 bits in ebay.sbf are used to store flash version
cl = C.readCookie("ebay","sbf");
if(!write){
return B.getMulti(cl,40,5);
}else if(write){
//storing cookielet value as 1 in case of zero
value=(value==0)?1:value;
C.writeCookielet("ebay","sbf",B.setMulti(cl, 40, 5, value));
}
}
})
.endType();
