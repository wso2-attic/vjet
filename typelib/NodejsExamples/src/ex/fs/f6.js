/**
 * Asynchronously writes data to a file. data can be a string or a buffer.
 */
var fs = require('fs') ;
fs.writeFile('message.txt', 'Hello Node', function (err) {
  if (err) throw err;
  console.log('It\'s saved!');
});