var win = function(r) {
    console.log("Code = " + r.responseCode);
    console.log("Response = " + r.response);
    console.log("Sent = " + r.bytesSent);
}

var fail = function(error) {
    alert("An error has occurred: Code = " + error.code);
}

var options = new FileUploadOptions();
options.fileKey="file";
options.fileName="newfile.txt";
options.mimeType="text/plain";

var params = new Object();
params.value1 = "test";
params.value2 = "param";

options.params = params;

var paths = navigator.fileMgr.getRootPaths();
var ft = new FileTransfer();
ft.upload(paths[0] + "newfile.txt", "http://some.server.com/upload.php", win, fail, options);
