vjo.ctype('BugJsFiles.Bug484extn')
.needs('BugJsFiles.GenericCtype', 'myAlias')
.props({
        main: function() { //< public void main (String...) 
        	this.vj$.myAlias.RADIUS;
        	var vv = this.vj$.myAlias();//<GenericCtype
        	vv.compute("Test");
        	
        }
}).endType();
