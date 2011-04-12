vjo.ctype('vjoPro.samples.foundations.Employee')
//snippet.withoutsatisfy.end
.satisfies('vjoPro.samples.fundamentals.IEmployer')
//snippet.withoutsatisfy.begin
.protos({
name:undefined, //< public String
empId:0, //< public int

//> public void constructs(String name, int empId)
constructs:function(name, empId) {
this.name = name;
this.empId = empId;
},

//> public int getEmpId()
getEmpId : function() {
return this.empId;
},

//> public String getName()
getName : function() {
return this.name;
}
})
.endType();
//snippet.withoutsatisfy.end
//snippet.withsatisfy.end
