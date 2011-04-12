vjo.ctype("vjoPro.samples.js.Developer")
.inherits("vjoPro.samples.js.Employee")
.protos({
manager: undefined, //< public String

//> public void constructs(String manager)
constructs : function (manager)
{
this.manager = manager;
}
})
.endType();
