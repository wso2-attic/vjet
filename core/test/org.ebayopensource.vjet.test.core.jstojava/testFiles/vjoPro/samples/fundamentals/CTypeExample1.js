vjo.ctype("vjoPro.samples.fundamentals.CTypeExample1")
.needs("vjoPro.samples.fundamentals.AbstractExample1")
.inherits("vjoPro.samples.fundamentals.AbstractExample1")
.props({
s_compoundedRate: undefined //< public Number
})
.protos({
m_enrolled: false, 		//< protected boolean
showAlert:function(greeting){ //< public void showAlert(String)
alert("Greeting from instance method of subclass: "+greeting);
}
})
.inits(
function(){
/* use this section if you require static initialization */
s_compoundedRate = 0.75;
}
)
.endType();
