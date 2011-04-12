vjo.ctype('vjoPro.samples.fundamentals.AbstractExample1')
.props({
s_rate: undefined, 		//< public double
s_discount: undefined, 	//<  double; default access control

s_greeting: function(greeting){	//< public void s_greeting(String)
alert('Greeting from static method defined in abstract class: '+greeting);
}
})
.protos({
	m_name: undefined, 	//< protected String
	m_bool: undefined, 	//< abstract boolean

//> public abstract void showAlert(String)
showAlert:  vjo.NEEDS_IMPL

})
.inits(
function(){
/* use this section if you require static initialization */
s_rate = 0.065;
}
)
.endType();
