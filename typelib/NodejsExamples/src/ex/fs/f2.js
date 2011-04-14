/**
 * Here is the synchronous version:
 */
var fs = require('fs'); 
fs.unlinkSync('/tmp/hello')
console.log('successfully deleted /tmp/hello');