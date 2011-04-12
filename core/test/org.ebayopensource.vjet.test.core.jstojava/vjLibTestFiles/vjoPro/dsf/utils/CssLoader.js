vjo.ctype("vjoPro.dsf.utils.CssLoader")
.props({
//>public void load(String psUrl)
load : function(psUrl) {
if(document.createStyleSheet){
document.createStyleSheet(psUrl);
}else {
var head = document.getElementsByTagName('head')[0], style = document.createElement("link");
style.rel =  "stylesheet";
style.type = "text/css";
style.href = psUrl;
head.appendChild(style);
}
}
})
.endType();
