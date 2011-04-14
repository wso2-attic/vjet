/* XXX Can this test be modified to not call the now-removed wait()? */

var common = require('../common');
var assert = require('assert');
var fs = require('fs') ;

console.log('first stat ...');

fs.stat(__filename,function(stats) {
    console.log('second stat ...');
    fs.stat(__filename)
        .timeout(1000)
        .wait();
     console.log('test passed');
    })
  .addErrback(function() {
      throw new Error();
    });
