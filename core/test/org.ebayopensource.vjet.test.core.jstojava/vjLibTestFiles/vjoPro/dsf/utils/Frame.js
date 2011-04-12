vjo.ctype("vjoPro.dsf.utils.Frame")
.props({
bust : function() {
var tl = top.location, dl = document.location;
if (tl != dl)
tl.replace(dl.href);
}
})
.endType();

