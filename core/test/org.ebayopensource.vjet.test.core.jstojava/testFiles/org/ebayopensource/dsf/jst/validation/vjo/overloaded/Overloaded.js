vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.overloaded.Overloaded') //< public
//>needs(org.ebayopensource.dsf.jst.validation.vjo.overloaded.IOverloaded)
.props({
	
	//> public void main(String... arguments)
	main:function(){
	    var x=new this.vj$.Overloaded("Justin"); //<Overloaded
	    var y=new this.vj$.Overloaded();//<Overloaded
	    var z=new this.vj$.Overloaded();//<Overloaded
	    
	    x.doIt();
	    y.doIt();
	    z.doIt(3);
	    
	    var iO = null;//<IOverloaded
	    iO.fire();
	    iO.fire(1);
	    iO.fire(1, "1");
	    
	    x.physical();
	    x.physical(1);
	    x.physical(1, "1");
	    
	    y.variable();
	    y.variable(1);
	    y.variable(1, "1");
	    y.variable(1, "1", new Date());
	    y.variable(1, "1", new Date(), new Date());
	    
	    z.moreOptions();
	    z.moreOptions(1);
	    z.moreOptions(1, "1");
	    z.moreOptions(1, "1", new Date());
	}
})
.protos({
	name: "", //<private String
		
	//> public void constructs(String name)
	//> public void constructs()
	constructs:function(name){
	    this.name=name;
	},
	
	//> public String doIt(int command)
	//> public String doIt()
	doIt: function(){
		return this.name;
	},
	
	//> public void increment()
	//> public void increment(int num)
	increment: function(num) {
	        this.doIt(num);
	},
	
	//> public void physical()
	//> public void physical(int num, String? name)
	physical: function() {
	
	},
	
	//> public void variable()
	//> public void variable(int num, String? name, Date... date)
	variable: function() {
	
	},
	
	//> public void moreOptions()
	//> public void moreOptions(int num, String? name, Date? date)
	moreOptions: function() {
	
	}
})
.endType();

