/**
 * With the asynchronous methods there is no guaranteed ordering. So the 
 * following is prone to error:
 */
var fs = require('fs') ;

fs.rename('/tmp/hello', '/tmp/world', function (err) {
  if (err) throw err;
  console.log('renamed complete');
});

fs.stat('/tmp/world', function (err, stats) {
  if (err) throw err;
  console.log('stats: ' + JSON.stringify(stats));
});