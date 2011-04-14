/**
 * Emitted when an exception bubbles all the way back to the event loop. If a 
 * listener is added for this exception, the default action (which is to print 
 * a stack trace and exit) will not occur.
 * <p>
 * Example of listening for uncaughtException:
 */
process.on('uncaughtException', function (err) {
  console.log('Caught exception: ' + err);
});

setTimeout(function () {
  console.log('This will still run.');
}, 500);

// Intentionally cause an exception, but don't catch it.
//>@SUPPRESSTYPECHECK ; MrP don't validate an intentional error
nonexistentFunc();
console.log('This will not run.');