var common = require('../common');
var assert = require('assert'); 

var net = require('net');
var util = require('util');
var fs = require('fs') ; 
var path = require('path') ;
var x = path.join(common.fixturesDir, 'x.txt');
var expected = 'xyz';

var client ; //< org.nodejs.net.Socket

var server = net.createServer(function(socket) {
  socket.addListener('receive', function(data) {
    found = data; ;
    client.end();
    socket.end();
    server.end();
    assert.equal(expected, found);
  });
});
server.listen(common.PORT);

client = net.createConnection(common.PORT);
client.addListener('connect', function() {
  fs.open(x, 'r', function(err, fd) {
    fs.sendfile(client.fd, fd, 0, expected.length, function(size) {
          assert.equal(expected.length, size);
      });
  });
});
