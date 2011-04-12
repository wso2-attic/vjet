vjo.ctype('vjoPro.samples.server.ServerEx6') //< public
.protos({
m_name:null,
/**
* @access public
* @param {String} name
*/
//> public constructs(String name)
constructs:function(name){
this.setName(1, name);
},
/**
* @access public
* @param {int} id
* @param {String} name
*/
//> public void setName(int id,String name)
setName:function(id, name){
if (id==1) {
this.m_name=name;
} else {
this.m_name="Will";
}
},
/**
* @access public
* @return String
*/
//> public String getName()
getName:function (){
return this.m_name;
}
})
.endType();
