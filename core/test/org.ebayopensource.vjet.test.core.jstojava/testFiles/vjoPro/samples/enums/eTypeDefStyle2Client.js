vjo.ctype('vjoPro.samples.enums.eTypeDefStyle2Client')
.needs('vjoPro.samples.enums.eTypeDefStyle2')
.props({
//> public void main(String[] args)
main:function(args){
document.writeln('this.vj$.eTypeDefStyle2.values() returns > ' + this.vj$.eTypeDefStyle2.values());
var mon = this.vj$.eTypeDefStyle2.MON;
document.writeln('this.vj$.eTypeDefStyle2.MON returns > ' + mon);
document.writeln('this.vj$.eTypeDefStyle2.MON.name() returns > ' + mon.name());
document.writeln('this.vj$.eTypeDefStyle2.MON.ordinal() returns > ' + mon.ordinal());
document.writeln('this.vj$.eTypeDefStyle2.MON.getDisplayName() returns > ' + mon.getDisplayName());
document.writeln('this.vj$.eTypeDefStyle2.MON.isWeekday() returns > ' + mon.isWeekday());
}
})
.endType();
