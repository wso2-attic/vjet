vjo.ctype('vjoPro.samples.fundamentals.AbstractCTypeClient')
.needs('vjoPro.samples.fundamentals.AbstractCTypeExample')
.props({
//> public void main(String[] args)
main: function(args){
//Call Static Method
vjoPro.samples.fundamentals.AbstractCTypeExample.getInitialValue();
}
})
.endType();
