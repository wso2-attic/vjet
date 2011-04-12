vjo.mtype('vjoPro.samples.mtype.Person3')
.satisfies('vjoPro.samples.mtype.Employer')
.protos({
deptid : 0, //< public int

//> public void doIt1()
doIt1:function(){
document.writeln('doIt1 called');
this.getId();
},

//> public int getDeptId()
getDeptId : function()
{
document.writeln('DeptId > ' + this.deptid);
return this.deptid;
}

})
.endType();
