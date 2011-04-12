vjo.ctype('vjoPro.samples.sample12.Employee')
.inherits('vjoPro.samples.sample12.Person')
.protos({
m_name:undefined, //< private String

//> public void constructs(String dept)
constructs:function(dept) {
this.base(dept);
},

//> public void setName(String name)
setName: function(name) {
this.base.setName(name);
this.m_name = name;
}
})
.endType();
