vjo.ctype('vjoPro.samples.server.ServerEx10') //< public
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
* @param {ServerEx6Jsr} poServerEx6
*/
//> public void doSomething(vjoPro.samples.server.ServerEx6Jsr  poServerEx6)
doSomething:function(poServerEx6){
alert("Employee ID: "+poServerEx6.getName());
}
})
.endType();
