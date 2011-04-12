vjo.ctype('vjoPro.samples.enums.eTypeDefStyle1Client')
.needs('vjoPro.samples.enums.eTypeDefStyle1')
.props({
//> public void main(String[] args)
main:function(args){
var etypeValues = this.vj$.eTypeDefStyle1.values();
document.writeln('eTypeDefStyle1 Values >' + etypeValues);
var etypeMon = this.vj$.eTypeDefStyle1.MON;
document.writeln('this.vj$.eTypeDefStyle1.MON returns > ' + etypeMon);
document.writeln('vjo.Enum instanceof eTypeDefStyle1.MON  > ' + vjo.Enum.instanceOf(this.vj$.eTypeDefStyle1.MON));

var etypeMonName = this.vj$.eTypeDefStyle1.MON.name();
document.writeln('this.vj$.eTypeDefStyle1.MON.name() returns > ' + etypeMonName);
document.writeln('this.vj$.eTypeDefStyle1.MON.ordinal() returns > ' + this.vj$.eTypeDefStyle1.MON.ordinal());
var sat = this.vj$.eTypeDefStyle1.SAT;
document.writeln('this.vj$.eTypeDefStyle1.SAT returns > ' + sat);
document.writeln('this.vj$.eTypeDefStyle1.SAT.name() returns > ' + sat.name());
document.writeln('this.vj$.eTypeDefStyle1.SAT.ordinal() returns > ' + sat.ordinal());
}
})
.endType();
