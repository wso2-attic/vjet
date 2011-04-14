/**
 * vm.runInNewContext compiles code to run in sandbox as if it were loaded from 
 * filename, then runs it and returns the result. Running code does not have 
 * access to local scope and the object sandbox will be used as the global 
 * object for code. sandbox and filename are optional.
 * <p>
 * Example: compile and execute code that increments a global variable and sets 
 * a new one. These globals are contained in the sandbox.
 */
var util = require('util') ;
var vm = require('vm') ;
var sandbox = {
      animal: 'cat',
      count: 2
    };

vm.runInNewContext('count += 1; name = "kitty"', sandbox, 'myfile.vm');
console.log(util.inspect(sandbox));

// { animal: 'cat', count: 3, name: 'kitty' }