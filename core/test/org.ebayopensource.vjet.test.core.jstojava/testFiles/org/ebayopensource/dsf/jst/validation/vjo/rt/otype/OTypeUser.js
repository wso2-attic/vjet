vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.rt.otype.OTypeUser")
//>needs(org.ebayopensource.dsf.jst.validation.vjo.rt.otype.OType)
//>needs(org.ebayopensource.dsf.jst.validation.vjo.rt.otype.OTypeNested)
.props({
	
	foo: function(objLiteral){//<void foo(OType::NVPair)
		alert(objLiteral.name);
		alert(objLiteral.value);
		alert(objLiteral);
	},

	bar: function(){//<void bar()
		//valid call with full arguments
		this.foo({
			name: "n",
			value: 10,
			callback: function(d){//<void function(Date)
				alert(d.getYear());
			}
		});
		
		//valid call, missing optional argument
		this.foo({
			name: "n",
			callback: function(d){//<void function(Date)
				alert(d.getYear());
			}
		});
		
		//invalid call, type not matching
		this.foo({
			name: 10,
			value: new Date(),
			callback: function(d){//<int function(Date)
				return 0;
			}
		});
		
		//invalid call, missing required properties
		this.foo({
			
		});
		
		//valid call, missing optional argument, with infer function
		this.foo({
			name: "n",
			callback: function(d){
				var str = d;//<String 
			}
		});
	},
	
	further: function(){
		var nvContract = null;//<OType::NVPair
		nvContract = {
			
		};
		
		var player = function(nv){//<OType::play
			alert(nv.name);
		};
		player(nvContract);
	},
	
	nested: function(){
		var enclosed = {
				nvContract : {}//<OTypeNested.Enclosed.NestedNV
				,
				nestedPlayer : function(nv){//<OTypeNested.Enclosed::nestedPlay
					alert(nv.label);
					nv.print();
				}
		};
		enclosed.nestedPlayer(enclosed.nvContract);
		
		//>OTypeNested.NV
		var nv = {
			
		};
		//this should be ok as either property or method is optional in NV
	}

}).endType();