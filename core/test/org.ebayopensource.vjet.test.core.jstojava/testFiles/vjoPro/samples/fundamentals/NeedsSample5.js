vjo.ctype('vjoPro.samples.fundamentals.NeedsSample5')
.needs('vjoPro.samples.fundamentals.NeedsSample6', 'MyNeeds')
.props({
//> public void main(String[] args)
main : function (args) {
document.writeln('Inside NeedsSample5 main()');
this.vj$.MyNeeds.doIt();
},

doIt : function () {
document.writeln('NeedsSample5 doIt() called');
}
})
.endType();
