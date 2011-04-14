var common = require('../common');
var net = require('net');

var path = process.ARGV[2];
var greeting = process.ARGV[3];

var receiver ; //< org.nodejs.net.Server
receiver = net.createServer(function(socket) {
  socket.addListener('fd', function(fd) {
// MrP - won't work in 0.40, use code after this comment
//    var peerInfo = process.getpeername(fd);
//    peerInfo.fd = fd;
//    var passedSocket = new net.Socket(peerInfo);
    var passedSocket = new net.Socket({fd: process.env.fd});

    passedSocket.addListener('eof', function() {
      passedSocket.close();
    });

    passedSocket.addListener('data', function(data) {
      passedSocket.send('[echo] ' + data);
    });
    passedSocket.addListener('close', function() {
      receiver.close();
    });
    passedSocket.send('[greeting] ' + greeting);
  });
});

/* To signal the test runne we're up and listening */
receiver.addListener('listening', function() {
  common.print('ready');
});

receiver.listen(path);
