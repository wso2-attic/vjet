vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.mtdinvocation.LocalMtdinvocation')
.props({
	b:function(){//<public void b()
		var f;//<void function(int)
		f(100);
		f("wrong");
		
		function g(i){//<void function(int)
			
		}
		
		g(100);
		g("wrong");
		
		//>void function(Date)
		function g2(i){
			
		}
		
		g2(new Date());
		g2("wrong");
		
		f = g;
		f = g2;
		
		//>void function(int)
		var h = function(i){
			
		};
		
		f = h;
	}
})
.endType();