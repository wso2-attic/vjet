
var fail = function(evt) {
	
    console.log(evt.target.error.code);
};

var paths = navigator.fileMgr.getRootPaths();
var reader = new FileReader();

reader.onload =  function(evt) {
    console.log(evt.target.result);
};


reader.onerror=  function(evt) {
    console.log(evt.target.error.code);
};
reader.readAsText(paths[0] + "readme.txt");
