/**
 * For UDP sockets, listen for datagrams on a named port and optional address. 
 * If address is not specified, the OS will try to listen on all addresses.
 * <p>Example of a UDP server listening on port 41234:
 */
var dgram = require("dgram");

var server = dgram.createSocket("udp4");
var messageToSend = new Buffer("A message to send"); 

server.on("message", function (msg, rinfo) {
  console.log("server got: " + msg + " from " +
    rinfo.address + ":" + rinfo.port);
});

server.on("listening", function () {
  var address = server.address();
  console.log("server listening " +
      address.address + ":" + address.port);
});

server.bind(41234);
// server listening 0.0.0.0:41234