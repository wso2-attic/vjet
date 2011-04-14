/**
 * File I/O is provided by simple wrappers around standard POSIX functions. To 
 * use this module do require('fs'). All the methods have asynchronous and 
 * synchronous forms.
 * <p>
 * The asynchronous form always take a completion callback as its last argument. 
 * The arguments passed to the completion callback depend on the method, but the 
 * first argument is always reserved for an exception. If the operation was 
 * completed successfully, then the first argument will be null or undefined.
 * <p>
 * Here is an example of the asynchronous version:
 */
var fs = require('fs');

fs.unlink('/tmp/hello', function (err) {
  if (err) throw err;
  console.log('successfully deleted /tmp/hello');
});