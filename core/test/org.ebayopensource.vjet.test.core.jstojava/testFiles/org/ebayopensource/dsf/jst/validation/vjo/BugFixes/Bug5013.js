vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5013') //< public
.props({
        //> public void foo()
        foo: function() {
			var arr = [{'name' : 'Raja', 'location' : 'SJC'}];
            for(var i=0;i<arr.length;i++){
                    var item = arr[i];
                    alert(item.name);
                    alert(item.location);
            }
        }
})
.endType();
