vjo.ctype('vjoPro.samples.foundations.Manager')
//snippet.inheritance.begin
.inherits('vjoPro.samples.foundations.Employee')
//snippet.inheritance.end
.protos({
project: undefined, //< public String

//> public void constructs(String project)
constructs : function (project)
{
this.base('Test_Name', 123);
this.project = project;
}
})
.endType();
