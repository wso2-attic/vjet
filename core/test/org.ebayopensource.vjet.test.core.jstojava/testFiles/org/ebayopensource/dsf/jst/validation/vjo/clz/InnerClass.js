vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.clz.InnerClass") //< public
.props({
        MyCache: vjo.ctype() //< private
        .props({
                cache: null //< final Array
        })
        .protos({
                //> private constructs()
                constructs: function() {
                        // empty
                        
                }
        })
        .inits(function(){
                this.cache = vjo.createArray(null, -(-128)+127+1);
                //{bugfix by roy, block stmt isn't supported by parser
                        for (var i=0; i < this.cache.length; i++) {
                                this.cache[i] = i-128;
                        }
                //}
        })
        .endType(),
        
        main: function(){
             for (var i=0; i < this.MyCache.cache.length; i++) {
                     alert(this.MyCache.cache[i]);
             }
        }
})
.endType();
