/*
This VjO class defines 
- a static property 's_name' which is initialized to 'undefined'
- a static method 'getName1' which returns the value of property 's_name'
- a static initialization block which initializes the static property 's_name' to 'World !'
- an instance property 'i_name' which has initial value of 'undefined'
- an instance method 'getName2' which returns the value of instance property 'i_name'
- a constructor which initializes the 'i_name' instance property
*/
//> public
vjo.ctype('access.scope.thiskeyword.HelloWorld')
.props({
	// Define your static members here

	s_name : undefined, //< public String

	//> public String getName1()
	getName1:function() {
		//get the static property using this keyword
		document.writeln('Hello ' + this.s_name);
		return this.s_name;
	}

})
.protos({
	// Define your instance members here

	i_name:undefined, //< public String

	//> public void constructs(String p_name)
	constructs:function(p_name) {
		// This is a constructor
		this.i_name = p_name;
	},

	//> public String getName2()
	getName2: function() {
		document.writeln('Hello ' + this.i_name);
		return this.i_name;
	}

})
.inits(
	function(){
		// Do your static initialization here
		this.s_name = 'World !';
	}
)
.endType(); //Every VjO Type must end with a call to endType()