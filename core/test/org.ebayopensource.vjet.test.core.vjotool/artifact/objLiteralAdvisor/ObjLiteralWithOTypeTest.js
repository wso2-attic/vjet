vjo.ctype('objLiteralAdvisor.ObjLiteralWithOTypeTest') //< public
//>needs(objLiteralAdvisor.OType)
.props({
	read: function(ol){//<void read(OType.message)
	
	},
	
	main: function(){
		this.read({
			id: "unique",
			fun: function(){//<public void fun()
		
			}
		});
	}
})
.endType();