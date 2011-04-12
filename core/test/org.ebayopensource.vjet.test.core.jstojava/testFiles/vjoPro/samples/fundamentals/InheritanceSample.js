vjo.ctype('vjoPro.samples.fundamentals.InheritanceSample')
.needs('vjoPro.samples.fundamentals.CTypeExample')
.props({
//> public void main(String[] args)
main: function(args){
//Call Static Method
vjoPro.samples.fundamentals.CTypeExample.getInitialValue();
//Call Instance Method
var ctypeobj = new vjoPro.samples.fundamentals.CTypeExample('Hello !'); //<CTypeExample
ctypeobj.showMessage();
}
})
.endType();
