/**
 * Similar to vm.runInThisContext but a method of a precompiled Script object. 
 * script.runInThisContext runs the code of script and returns the result. 
 * Running code does not have access to local scope, but does have access to the 
 * global object (v8: in actual context).
 * <p>
 * Example of using script.runInThisContext to compile code once and run it 
 * multiple times:
 */
var vm = require('vm');

globalVar = 0;

var script = vm.createScript('globalVar += 1', 'myfile.vm');

for (var i = 0; i < 1000 ; i += 1) {
  script.runInThisContext();
}

console.log(globalVar);

// 1000