/**
 * This class is a subclass of net.Server and has the same methods on it. 
 * Instead of accepting just raw TCP connections, this accepts encrypted 
 * connections using TLS or SSL.
 * <p>Here is a simple example echo server:
 */
var tls = require('tls'); 
var fs = require('fs'); 

var options = {
  key: fs.readFileSync('server-key.pem'),
  cert: fs.readFileSync('server-cert.pem')
};

tls.createServer(options, function (s) {
  s.write("welcome!\n");
  s.pipe(s);
}).listen(8000);