/**
 * Similar to vm.runInNewContext a method of a precompiled Script object. 
 * script.runInNewContext runs the code of script with sandbox as the global 
 * object and returns the result. Running code does not have access to local 
 * scope. sandbox is optional.
 * <p>
 * Example: compile code that increments a global variable and sets one, then 
 * execute this code multiple times. These globals are contained in the sandbox.
 */
var util = require('util') ; 
var vm = require('vm') ; 
var sandbox = {
      animal: 'cat',
      count: 2
    };

var script = vm.createScript('count += 1; name = "kitty"', 'myfile.vm');

for (var i = 0; i < 10 ; i += 1) {
  script.runInNewContext(sandbox);
}

console.log(util.inspect(sandbox));

// { animal: 'cat', count: 12, name: 'kitty' }