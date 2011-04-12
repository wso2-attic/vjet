vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5079') //< public
.props({
        //> public void foo()
        foo: function() {
			'abcdefghi'.match(/(a(b(c(d(e)f)g)h)i)/);
            alert(RegExp.$1);
            alert(RegExp.$2);
            alert(RegExp.$3);
            alert(RegExp.$4);
            alert(RegExp.$5);
            alert(RegExp.$6);
            alert(RegExp.$7);
            alert(RegExp.$8);
            alert(RegExp.$9); 
            RegExp.input = "abcd12357efg";
			alert(String(/\d+/.exec('2345')));
			alert(String(/\d+/.exec('2345')));
			'foo'.match(/foo/);
			alert(RegExp.lastMatch);
			alert(RegExp.lastParen);
			alert(RegExp.leftContext);
			alert(RegExp.rightContext);
        }
})
.endType();

