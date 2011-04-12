vjo.mtype('vjoPro.samples.mtype.Person2')
//snippet.mixin.begin
.expects('vjoPro.samples.mtype.Employer')
//snippet.mixin.end
.protos({
//> public void doIt1()
doIt1:function(){
document.writeln('doIt1 called');
this.getId();
this.getDeptId();
}
})
.endType();
