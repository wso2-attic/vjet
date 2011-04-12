vjo.ctype('vjoPro.samples.fundamentals.NeedsSample6')
.needs('vjoPro.samples.fundamentals.NeedsSample5', 'MyNeeds')
.props({
//> public void main(String[] args)
main : function (args) {
document.writeln('Inside NeedsSample6 main()');
this.vj$.MyNeeds.doIt();
},

doIt : function () {
document.writeln('NeedsSample6 doIt() called');
}
})
.endType();
