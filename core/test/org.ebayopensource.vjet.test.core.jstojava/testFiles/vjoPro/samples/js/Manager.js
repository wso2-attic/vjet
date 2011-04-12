vjo.ctype("vjoPro.samples.js.Manager")
.inherits("vjoPro.samples.js.Employee")
.protos({
project: undefined, //< public String

//> public void constructs(String project)
constructs : function (project)
{
this.project = project;
}
})
.endType();
