vjo.ctype('vjoPro.samples.mtype.Employee2')
//snippet.mixin.begin
.mixin('vjoPro.samples.mtype.Person2')
//snippet.mixin.end
.props({

//> public void main(String[] args)
main : function(args)
{
var emp = new this.vj$.Employee2(100, 111);//<Employee2
emp.doIt1();
}
})
.protos({

empid : 0, //< public int
deptid : 0, //< public int

//public void contructs(int id)
constructs : function(id, dptid)
{
this.empid = id;
this.deptid = dptid;
},

//> public int getId()
getId : function()
{
document.writeln('EmpId > ' + this.empid);
this.empid;
},

//> public int getDeptId()
getDeptId : function()
{
document.writeln('DeptId > ' + this.deptid);
return this.deptid;
}
})
.endType();
