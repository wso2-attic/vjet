vjo.ctype('vjoPro.samples.fundamentals.NeedsSample3')
.needs(['vjoPro.samples.fundamentals.A','vjoPro.samples.fundamentals.B'])
.props({
//> public void main(String[] args)
main : function (args) {
document.writeln('main() called');
this.vj$.A.doA();
this.vj$.B.doB();
}
})
.endType();
