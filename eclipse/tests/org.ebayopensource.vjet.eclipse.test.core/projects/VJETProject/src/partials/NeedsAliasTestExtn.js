vjo.needs('partials.Bug2149TypeB', 'MyAlias');
vjo.ctype('partials.NeedsAliasTestExtn')
.props({
 	main: function() { //< public void main (String ... arguments) 
    	var x = this.vj$.MyAlias(); //< partials.Bug2149TypeB
    	x.
  	}
}).endType();