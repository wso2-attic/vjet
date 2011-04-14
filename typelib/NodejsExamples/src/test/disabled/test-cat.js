var common = require('../common.js'); //<< test.common
var assert = require('assert');
var http = require('http');
var path = require('path') ;

console.log('hello world');

var body = 'exports.A = function() { return "A";}';
var server = http.createServer(function(req, res) {
  console.log('req?');
  res.writeHead(200, {
    'Content-Length': body.length,
    'Content-Type': 'text/plain'
  });
  res.write(body);
  res.end();
});
server.listen(common.PORT); 

var errors = 0;
var successes = 0;

var promise = path.cat('http://localhost:' + common.PORT, 'utf8');

promise.addCallback(function(content) {
  assert.equal(body, content);
  server.close();
  successes += 1;
});

promise.addErrback(function() {
  errors += 1;
});

var dirname = path.dirname(__filename);
var fixtures = path.join(dirname, 'fixtures');
var x = path.join(fixtures, 'x.txt');

promise = path.cat(x, 'utf8');

promise.addCallback(function(content) {
  assert.equal('xyz', content.replace(/[\r\n]/, ''));
  successes += 1;
});

promise.addErrback(function() {
  errors += 1;
});

process.addListener('exit', function() {
  assert.equal(2, successes);
  assert.equal(0, errors);
});
