/**
 * Begin accepting connections on the specified port and host. If the host is 
 * omitted, the server will accept connections directed to any IPv4 address 
 * (INADDR_ANY).
 * <p>
 * This function is asynchronous. The last parameter callback will be called 
 * when the server has been bound.
 * <p>
 * One issue some users run into is getting EADDRINUSE errors. Meaning another 
 * server is already running on the requested port. One way of handling this 
 * would be to wait a second and the try again. This can be done with
 */
var net = require('net'); 
var server = net.createServer(function(){}) ;
var PORT=1212, HOST='somewhere.com'
server.on('error', function (e) {
  if (e.code == 'EADDRINUSE') {
    console.log('Address in use, retrying...');
    setTimeout(function () {
      server.close();
      server.listen(PORT, HOST);
    }, 1000);
  }
});