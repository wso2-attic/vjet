vjo.ctype('vjoPro.samples.sample10.Employee')
.props({
DEFAULTGROUP:100, //< public int

//> public  int getDefaultGroup()
getDefaultGroup:function() {
return this.DEFAULTGROUP;
}
})
.inits( function () {
this.DEFAULTGROUP=0;
})
.endType();
