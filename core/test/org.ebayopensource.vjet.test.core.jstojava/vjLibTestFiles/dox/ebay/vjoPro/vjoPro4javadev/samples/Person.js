vjo.ctype("dox.ebay.vjoPro.vjoPro4javadev.samples.Person")
//snippet.vjoProtype.end
.protos({
m_name:null,
m_age:0,
/**
* @access public
* @param {String} name
*/
constructs:function(name){
this.setName(name);
},
/**
* @access public
* @param {String} name
*/
setName:function(name){
this.m_name=name;
},
/**
* @access public
* @return String
*/
getName:function (){
return this.m_name;
}
})
.endType();
//snippet.vjoProprotos.end
