vjo.ctype('vjoPro.samples.mtype.Employee')
.satisfies('vjoPro.samples.mtype.Employer')
.props({

//> public void main(String[] args)
main : function(args)
{
var emp = new this.vj$.Employee(100, 111);//<Employee
emp.getDeptId();
emp.getId();
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
this.deptid;
}

})
.endType();
