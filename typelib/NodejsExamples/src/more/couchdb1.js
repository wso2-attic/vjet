var http = require('http') ;
var url = require('url') ;
var fs = require('fs') ;
var io = require('./vendor/socket.io');

var server = http.createServer(function() {});
server.listen(8080);
var socket = io.listen(server);

// couchdb connection data
var db = {};
db.user = '';
db.pass = '';
db.host = '';

if (db.user && db.pass) {
    var basicAuth = 'Basic ' + new Buffer(db.user + ':' + db.pass).toString('base64');
}

var headers = {};
if (typeof basicAuth != 'undefined') {
    headers["Authorization"] = basicAuth;
}
headers['Content-Type'] = 'application/json';

var requestUri = 'http://www.google.com/rest/api/finance/snp';

// request changes
var client  = http.createClient(80, db.host); //<<
var changes = client.request('GET', requestUri, headers); //<<
changes.end();

// handle response, it'll be chunked
changes.on('response', function (response) { //< void f(org.nodejs.http.ClientResponse)
    // bail hard on non-200, something must be wrong
    if (response.statusCode != 200) {
        throw "response: " + response.statusCode;
    }

    var json;
    response.setEncoding('utf8');
    response.on('data', function (chunk) {
        try {
            json = JSON.parse(chunk); // let's not get crazy here
            socket.broadcast({'foo': json.doc.foo});
        } catch (Err) {
            //console.log("skip: " + sys.inspect(Err));
            return; // continue
        }
    });
});
