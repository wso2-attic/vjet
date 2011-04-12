vjo.ctype('vjoPro.samples.classes.PersonWithJSDependency') //< public
.needs('vjoPro.dsf.client.Browser')
.protos({
m_name:null,
m_age:0,
/**
* @access public
* @param {String} name
*/
//> public constructs(String name)
constructs:function(name){
this.setName(name);
},
/**
* @access public
* @param {String} name
*/
//> public void setName(String name)
setName:function(name){
this.m_name=name;
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
