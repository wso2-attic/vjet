/**
* Type: vjo.samples.fundamentals.AbstractExample1
* Description:
*/
//> public abstract
vjo.ctype('syntax.abstraceClass.AbstractExample1')
.props({
	s_rate: undefined, 		//< public double
	s_discount: undefined, 	//<  double; default access control

	s_greeting: function(greeting){	//< public void s_greeting(String)
		alert('Greeting from static method defined in abstract class: '+greeting);
	}
})
.protos({

	m_name: undefined, 	//< protected String
	m_bool: false, 		//< boolean

	//> public abstract void showAlert(String greeting)
	showAlert: vjo.NEEDS_IMPL,

	//> public abstract void showAlert234(String msg)
	showAlert234: vjo.NEEDS_IMPL
})
.inits(
	function(){
		/* use this section if you require static initialization */
		this.s_rate = 0.065;
	}
)
.endType();