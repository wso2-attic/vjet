vjo.ctype("dox.ebay.vjoPro.vjoPro4javadev.samples.JavaProxySample")
.needs("dox.ebay.vjoPro.vjoPro4javadev.samples.JavaProxyAbstract")
.inherits("dox.ebay.vjoPro.vjoPro4javadev.samples.JavaProxyAbstract")
.satisfies("dox.ebay.vjoPro.vjoPro4javadev.samples.JavaProxyInterface")
// snippet.usingmtype.begin
.mixin("dox.ebay.vjoPro.vjoPro4javadev.samples.JavaProxyMixin")
// snippet.usingmtype.end
.props({

// snippet.staticVariable.begin
// static variable
staticVariable:20, // <public int
// snippet.staticVariable.end


// snippet.finalVariable.begin
// final variable
staticFinalVariable:10, // <final public int
// snippet.finalVariable.end

// snippet.staticMethod.begin
// > public void staticMethod()
staticMethod:function() {
alert("staticMethod()");
}
// snippet.staticMethod.end
})

.protos({
// snippet.instanceVariable.begin
// instance variable
instanceVariable:10, // < public int
// snippet.instanceVariable.end
instanceVariable1:20, // < public int

// snippet.instanceFinal.begin
instanceFinalVariable:10, // <final public int
// snippet.instanceFinal.end

// snippet.instanceMethod.begin
// > public void instanceMethod ()
instanceMethod: function() {
alert("instanceMethod()");
},
// snippet.instanceMethod.end

// snippet.overloadMethod.begin
//>public void overloadMethod ()
//>public void overloadMethod (String s)
overloadMethod: function(s) {
alert("overloadMethod(String)");
},
// snippet.overloadMethod.end

// snippet.interfaceMethodImpl.begin
//> public void interfaceMethod()
interfaceMethod : function () {
alert("interfaceMethod()");
},
// snippet.interfaceMethodImpl.end

// snippet.abstractMethodImpl.begin
//>public void abstractMethod()
abstractMethod : function () {
alert("impl of > abstractMethod()");
},
// snippet.abstractMethodImpl.end



// snippet.methodWithParameter.begin
// > public void instanceMethodWithParameter(int i, String s)
instanceMethodWithParameter: function(i,s) {
alert("instanceMethodWithParameter(int, String), value="+ss);
},
// snippet.methodWithParameter.end

// > public void instanceMethodWithParameter(int i)
instanceMethodWithOneParameter: function(i) {
alert("instanceMethodWithParameter(int), value="+i);
},

// snippet.constructor.begin
// >public constructs(int i)
constructs:function(i){
}
// snippet.constructor.end
})
.endType();
