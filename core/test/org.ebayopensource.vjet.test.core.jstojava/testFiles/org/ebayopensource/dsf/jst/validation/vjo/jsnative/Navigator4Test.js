vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.jsnative.Navigator4Test')
.props({
        //>public void foo() 
        foo : function(){
                var n = navigator; //<Navigator
                var pd, id, swf = "Shockwave Flash",v;
            if (n.plugins[swf]) 
            {
                    pd = n.plugins[swf].description;
                    id = pd.indexOf("Flash")+5;
                        v = parseInt(pd.substr(id,pd.length));
            }
        }      
})
.endType();
