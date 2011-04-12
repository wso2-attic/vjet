vjo.mtype('vjoPro.samples.mtype.Person1')
.props({
//> public void doIt1()
doIt1:function(){
document.writeln('doIt1 called');
}
})
.protos({
//> public void doIt2()
doIt2:function(){
document.writeln('doIt2 called');
}
})
.endType();
