vjo.itype('vjoPro.samples.fundamentals.InterfaceWithSubType')
.needs('vjoPro.samples.fundamentals.SubordinateTypeUsages1')
.protos({
//> public void print(int copies, boolean color)
print: vjo.NEEDS_IMPL,

//> public vjoPro.samples.fundamentals.SubordinateTypeUsages1 getPrint()
getPrint: vjo.NEEDS_IMPL,

//> void doPrint(vjoPro.samples.fundamentals.SubordinateTypeUsages1 p)
doPrint: vjo.NEEDS_IMPL
})
.props({
//> void showMessage(String msg)
showMessage: function(msg) { }
})
.endType();
