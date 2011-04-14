/**
 * An array containing the command line arguments. The first element will be 
 * 'node', the second element will be the name of the JavaScript file. The next 
 * elements will be any additional command line arguments.
 */
// print process.argv
process.argv.forEach(function (val, index, array) { 
  console.log(index + ': ' + val);
});