vjo.ctype('vjoPro.samples.server.ServerEx7') //< public
.protos({
m_id:null,
/**
* @access public
* @param {int} psId
*/
//> public constructs(int psId)
constructs:function(psId){
this.m_id = psId;
},
/**
* @access public
* @param {String} name
*/
//> public void doSomething(String name)
doSomething:function(name){
alert("Employee ID: "+this.m_id+" ,name:"+name)
}
})
.endType();
