vjo.ctype('access.scope.thiskeyword.PropsThis1')
.protos({
	m_publicAddress1:undefined, //< private String

	//> public void setAddress1(String address, String id)
	setAddress1: function(address, id){
		this.m_publicAddress1 = address;
	},
	
	//> private void modAddress1(String address, String name)
	modAddress1: function(address, name){
		this.x = 30;
	},
	
		//> private void modAddress2(String address, String name)
	modAddress2: function(address, name){
		this.vj$.PropsThis1.x = 30;
		this.modAddress1(address, name);
		this.modAddress2(address, name);
	}
})
.props({
	
	x : 30, //<public int

	//> public void main(String[] args)
	main : function(args)
	{
		this.m_publicAddress1 = "String";
	}
})
.endType();