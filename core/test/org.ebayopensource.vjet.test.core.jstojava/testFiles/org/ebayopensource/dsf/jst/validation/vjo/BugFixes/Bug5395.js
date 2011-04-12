vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5395")
.props({
	//> public void resize(String,int,int);
    resize : function(psId, piWidth, piHeight) {
        var d = document, e = d[psId]||d.images[psId], ow, oh, nw, nh, arw, arh, ar;
        if(e){
            //getting original width and height.
            ow = e.width;
            oh = e.height;
            //calcualting aspect ratio
            arw = ow/piWidth;
            arh = oh/piHeight;
            ar = (arw> arh) ? arw : arh;
            if(ar >= 1){
                nw = ow/ar;
                nh = oh/ar;
            }else{
                nw = ow;
                nh = oh;
            }
            e.width = nw;
            e.height = nh;
        }
    }   
})
.endType();