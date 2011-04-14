/**
 * It could be that fs.stat is executed before fs.rename. The correct way to do 
 * this is to chain the callbacks.
 */
var fs = require('fs') ; 

fs.rename('/tmp/hello', '/tmp/world', function (err) {
  if (err) throw err;
  fs.stat('/tmp/world', function (err, stats) {
    if (err) throw err;
    console.log('stats: ' + JSON.stringify(stats));
  });
});