/**
 * Asynchronously reads the entire contents of a file. Example:
 */
var fs = require('fs') ;

fs.readFile('/etc/passwd', function (err, data) {
  if (err) throw err;
  console.log(data);
});