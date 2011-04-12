vjo.ctype('vjoPro.samples.fundamentals.NeedsSample4')
.needs('vjoPro.samples.fundamentals.A')
.needs('vjoPro.samples.fundamentals.B','MyB')
.props({
//> public void main(String[] args)
main : function (args) {
document.writeln('main() called');
this.vj$.A.doA();
this.vj$.MyB.doB();
}
})
.endType();
