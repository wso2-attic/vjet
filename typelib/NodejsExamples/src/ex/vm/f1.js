/**
 * vm.runInThisContext() compiles code as if it were loaded from filename, runs 
 * it and returns the result. Running code does not have access to local scope. 
 * filename is optional.
 * <p>Example of using vm.runInThisContext and eval to run the same code:
 */
var localVar = 123 ;
var vm = require('vm');

var usingscript = vm.runInThisContext('localVar = 1;', 'myfile.vm');
console.log('localVar: ' + localVar + ', usingscript: ' + usingscript);
var evaled = eval('localVar = 1;');
console.log('localVar: ' + localVar + ', evaled: ' + evaled);

// localVar: 123, usingscript: 1
// localVar: 1, evaled: 1