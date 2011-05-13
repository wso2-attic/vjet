var win = function(evt) {
    console.log(evt.target.result);
};
var fail = function(evt) {
    console.log(evt.target.error.code);
};

var paths = navigator.fileMgr.getRootPaths();
var reader = new FileReader();
reader.onload = win;
reader.onerror= fail;
reader.readAsText(paths[0] + "readme.txt");
