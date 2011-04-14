var fs = require('fs') ;
var sys = require('sys');
var http = require('http');

http.createServer(function (req, res) {
  checkBalanceFile(req, res);
}).listen(8000);

// Try all flavors of the callback
function cb1() {}
function cb2(err) {}
function cb3(err, stats) {}
var cba = function() {}
var cbb = function(err) {}
var cbc = function(err, stats) {}
fs.stat("balance") ; 
fs.stat("balance", cb1) ;
fs.stat("balance", cb2) ;
fs.stat("balance", cb3) ;
fs.stat("balance", cba) ;
fs.stat("balance", cbb) ;
fs.stat("balance", cbc) ;
fs.stat("balance", function() {}) ;
fs.stat("balance", function(err){})
fs.stat("balance", function(err, stats) {}) 

//> void f(org.nodejs.http.ServerRequest, org.nodejs.http.ServerResponse)
function checkBalanceFile(req, res) { 
  fs.stat("balance", function(err) { 
	
    if (err) {
      setTimeout(function() {
    	  checkBalanceFile(req, res)}, 1000);
    } 
    else {
      passThroughOriginalRequest(req, res);
    }
  });
}

//> void f(org.nodejs.http.ServerRequest, org.nodejs.http.ServerResponse)
function passThroughOriginalRequest(req, res) {
  var request = http.createClient(2000, "localhost").request("GET", req.url, {});
  request.addListener("response", function (response) { //< void f(org.nodejs.http.ClientResponse)
    res.writeHead(response.statusCode, response.headers);
    response.addListener("data", function (chunk) {
      res.write(chunk);
    });
    response.addListener("end", function () {
      res.close();
    });
  });
  request.close();
}

sys.puts('Server running at http://127.0.0.1:8000/');
