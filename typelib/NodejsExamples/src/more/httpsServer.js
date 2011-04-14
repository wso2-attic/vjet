var crypto = require('crypto') ;
var fs = require("fs") ;
var http = require("http");

var privateKey = fs.readFileSync('privatekey.pem').toString();
var certificate = fs.readFileSync('certificate.pem').toString();

var credentials = crypto.createCredentials(
	{key: privateKey, cert: certificate}); //<<

//> void f(org.nodejs.http.ServerRequest, org.nodejs.http.ServerResponse)
var handler = function (req, res) {
  res.writeHead(200, {'Content-Type': 'text/plain'});
  res.end('Hello World\n'); 
};

var server = http.createServer(); 
server.setSecure(credentials);
server.addListener("request", handler);
server.listen(8000);