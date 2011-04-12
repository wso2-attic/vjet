vjo.ctype("dox.ebay.vjoPro.vjoPro4javadev.samples.sample1.Employee")
.needs("dox.ebay.vjoPro.vjoPro4javadev.samples.sample1.base.Person")
.inherits("dox.ebay.vjoPro.vjoPro4javadev.samples.sample1.base.Person")
.satisfies("dox.ebay.vjoPro.vjoPro4javadev.samples.sample1.Employer")
.props({
defaultgroup:100, //< public int

//> public int getDefaultGroup()
getDefaultGroup:function() {
//get the static property using this keyword
alert(this.defaultgroup);
//get the static property using fully qualified VjO class name
alert(dox.ebay.vjoPro.vjoPro4javadev.samples.sample1.Employee.DEFAULTGROUP);
return this.defaultgroup;
},

//> public void setDefaultGroup(int defaultgroup)
setDefaultGroup:function(defaultgroup) {
alert("Default Group >>" + defaultgroup);
}

})
.protos({
m_name:null, //< public String
m_age:0, //< private int

//> public constructs()
constructs:function() {
},

//> public void setName(String name)
setName: function(name) {
alert("Set Name to > " + name);
this.m_name = name;
},

//> public String getName()
getName: function() {
alert("Name is > " + this.m_name);
return this.m_name;
},

//> public void setAge(int age)
setAge:function(age){
this.m_age = age;
},

//> public int getAge()
getAge:function() {
return this.m_age;
}
})
.endType();
