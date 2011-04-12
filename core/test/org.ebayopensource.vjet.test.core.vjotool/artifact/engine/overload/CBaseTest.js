vjo.ctype('engine.overload.CBase') //< public
.props({
	
	//>private double pvtStaticCompute(int i, String s, Date d)
	//>private double pvtStaticCompute(int i, String s)
	//>private double pvtStaticCompute(int i)
	//>private double pvtStaticCompute()
	pvtStaticCompute : function(){
		return 10;
	},
	
	//>protected double proStaticCompute(int i, String s, Date d)
	//>protected double proStaticCompute(int i, String s)
	//>protected double proStaticCompute(int i)
	//>protected double proStaticCompute()
	proStaticCompute : function(){
		return 10;
	},
	
	//>public double pubStaticCompute(int i, String s, Date d)
	//>public double pubStaticCompute(int i, String s)
	//>public double pubStaticCompute(int i)
	//>public double pubStaticCompute()
	pubStaticCompute : function(){
		return 10;
	},
	
	//>public void main(String... args) 
	main : function(args){
		<<1>>var val = <<2>>this.<<3>>pubStaticCompute();
		func2(<<4>>val);
	},
	
	//>public void func2(double i) 
	func2 : function(value){
		
		var pubStatComp = this.pubStaticCompute(10,"PQR", new Date());
		
		var base = new this.vj$.CBase(); //< CBase
		var pubComp = base.<<6>>pubCompute(10,"PQR", new Date());
		
		if (<<7>>this.<<8>>pubStaticCompute()== <<9>>1) {
			<<10>>
		} else {
			<<11>>
		}
		
		var i;
		for (<<12>>i in <<13>>this.<<14>>pubStaticCompute()) {
			<<15>>
		}
			
	}

})
.protos({
	
	//>private double pvtCompute(int i, String s, Date d)
	//>private double pvtCompute(int i, String s)
	//>private double pvtCompute(int i, Date d)
	//>private double pvtCompute(int i)
	//>private double pvtCompute()
	pvtCompute : function(){
		return 10;
	},
	
	//>protected double proCompute(int i, String s, Date d)
	//>protected double proCompute(int i, String s)
	//>protected double proCompute(int i)
	//>protected double proCompute()
	proCompute : function(){
		return 10;
	},
	
	//>public double pubCompute(int i, String s, Date d)
	//>public double pubCompute(int i, String s)
	//>public double pubCompute(int i)
	//>public double pubCompute()
	pubCompute : function(){
		return 10;
	},
	
	//>public double mixCompute(int i, String s, Date d)
	//>protected double mixCompute(int i, String s)
	//>double mixCompute(int i)
	//>private double mixCompute()
	mixCompute : function(){
		return 10;
	},
	
	//>public void callFunc1() 
	callFunc1 : function(){
		<<16>>this.func1(<<17>>this.pvtCompute());
	},
	
	//>public void func1(double i) 
	func1 : function(value){
		var i = <<18>>this.pubCompute();
		
		if (<<19>>i == <<20>>this.vj$.CBase.<<21>>pubStaticCompute()) {
			<<22>>
		} else {
			<<23>>
		}
	}

})
.inits(function(){
	<<24>>this.pubStaticCompute();
})
.endType();