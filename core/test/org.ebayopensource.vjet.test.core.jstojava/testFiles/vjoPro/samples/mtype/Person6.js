vjo.mtype('vjoPro.samples.mtype.Person6')
.props({
gender : 'Male', //< public String

//> public String getGender()
getGender: function()
{
return this.gender;
}
})
.protos({
//> public void printGender()
printGender:function(){
//snippet.mixin.begin
document.writeln('Gender > ' + this.vj$.Person6.getGender());
//snippet.mixin.end
}
})
.endType();
