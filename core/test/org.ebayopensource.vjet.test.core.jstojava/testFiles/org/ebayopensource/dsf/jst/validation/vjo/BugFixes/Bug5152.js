vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5152') //< public
.needs(['org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5152CType'])
//>needs org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5152IType
.props({
        //> public void main()
        main: function() {
        	var test = new this.vj$.Bug5152CType(); //< Bug5152IType
        	test.foo();
        }
})
.endType();
