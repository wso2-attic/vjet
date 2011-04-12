vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.BugCatchException')
.props({

	scopedEx: function(){
		try{
			alert('nothing');
		}
		catch(E){
			alert(E.name);
			alert(E.message);
		}
		catch(E){
			alert(E.name);
			alert(E.message);
		}
	},
	
	//> public boolean missingRtn()
	missingRtn: function(){
		try{
			alert('nothing');
		}
		catch(E){
			return false;
		}
	},
	
	//> public boolean correctRtn()
	correctRtn: function(){
		try{
			alert('nothing');
		}
		catch(E){
			return false;
		}
		
		return true;
	},
	
	//> public boolean forceRtnInFinally()
	forceRtnInFinally: function(){
		try{
			alert('nothing');
		}
		catch(E){
			return false;
		}
		finally{
			return true;
		}
	}

}).endType();