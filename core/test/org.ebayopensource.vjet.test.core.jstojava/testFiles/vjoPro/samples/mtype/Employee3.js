vjo.ctype('vjoPro.samples.mtype.Employee3')
//snippet.mixin.begin
.mixin('vjoPro.samples.mtype.Person3')
//snippet.mixin.end
.props({

//> public void main(String[] args)
main : function(args)
{
//snippet.mixin.begin
var emp = new this.vj$.Employee3(100,111);//<Employee3
emp.doIt1();
emp.getDeptId();
//snippet.mixin.end
}
})
.protos({

empid : 0, //< public int

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
}
})
.endType();
