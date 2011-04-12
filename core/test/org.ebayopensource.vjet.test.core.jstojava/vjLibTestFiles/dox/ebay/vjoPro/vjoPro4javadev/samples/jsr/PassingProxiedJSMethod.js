vjo.ctype("dox.ebay.vjoPro.vjoPro4javadev.samples.jsr.PassingProxiedJSMethod")
.props({
m_DefaultID:100, //< public int

//> public String getDefaultID()
getDefaultID: function() {
return this.m_DefaultID;
}
})
.protos({
m_ID:0, //< public int

//> public void setID(String id)
setID: function(id) {
alert(id);
this.m_ID = id;
}
})
.endType();
