/**
 * For Unix domain datagram sockets, start listening for incoming datagrams on 
 * a socket specified by path. Note that clients may send() without bind(), but 
 * no datagrams will be received without a bind().
 * <p>Example of a Unix domain datagram server that echoes back all messages it 
 * receives:
*/
var dgram = require("dgram");
var serverPath = "/tmp/dgram_server_sock";
var server = dgram.createSocket("unix_dgram");

server.on("message", function (msg, rinfo) {
  console.log("got: " + msg + " from " + rinfo.address);
  server.send(msg, 0, msg.length, rinfo.address);
});

server.on("listening", function () { 
  console.log("server listening " + server.address().address);
})

server.bind(serverPath);