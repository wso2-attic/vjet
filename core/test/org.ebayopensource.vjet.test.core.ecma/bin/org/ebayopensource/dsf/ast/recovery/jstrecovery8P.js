vjo.ctype('a.b.c.MyVjoType2') //< public
.satisfies('a.b.c.MyVjoInterface')
.needs('t.u.v.A')
.props({
	abc: "ehll"
})
.protos({
    abcd:"asdsf"
})
.inits(	
	function(){
	this.abc = window.$missing2;
}).endType();