vjo.ctype('vjoPro.samples.fundamentals.NeedsSample1')
.needs('vjoPro.samples.fundamentals.A')
.props({
//> public void main(String[] args)
main : function (args) {
document.writeln('main() called');
this.vj$.A.doA();

//doA() can alternatively be called like this
vjoPro.samples.fundamentals.A.doA();
}
})
.endType();
