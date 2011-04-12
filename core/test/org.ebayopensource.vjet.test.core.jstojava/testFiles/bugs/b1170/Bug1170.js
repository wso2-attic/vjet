vjo.ctype('bugs.b303.Bug1170')
.props({
        e1 : vjo.etype("boo").values("HA", "BA").endType(),
        e2 : vjo.etype("foo").values("JA", "BLAH").endType(),
        getBob : function(){
                return this.vjo.NamesEnum.BOB;
        },
        e4 : this.vjo.NamesEnum

}).endType()