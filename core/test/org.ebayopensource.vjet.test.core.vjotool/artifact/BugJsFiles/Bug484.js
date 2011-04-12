vjo.ctype('BugJsFiles.Bug484')
.needs('BugJsFiles.GenericCtype', 'myAlias')
.props({
        main: function() { //< public void main (String...) 
        	this.vj$.myAlias.RADIUS;
        	var v = this.vj$.myAlias();//<GenericCtype
        	v.compute("Test");        	
        }
}).endType();
