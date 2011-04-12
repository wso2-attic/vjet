vjo.ctype('vjoPro.samples.fundamentals.NeedsSample2')
.needs('vjoPro.samples.fundamentals.A', 'MyA')
.props({
//> public void main(String[] args)
main : function (args) {
document.writeln('main() called');
this.vj$.MyA.doA();
}
})
.endType();
