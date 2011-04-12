vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.jsnative.Dom')
.props({

	main: function(){
		var psId = "a"; //<String
		var d = document; //<HTMLDocument
		var e = d.getElementById(psId); 
        if (!e && d.all){
                e = d.all[psId];//<<HTMLElement
        }
        
        var s = e;//<<HTMLSelectElement;
        var b = s.options;
        var c = b.length;
	}
	
}).endType();