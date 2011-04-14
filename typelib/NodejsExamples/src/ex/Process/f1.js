/**
 * Emitted when the process is about to exit. This is a good hook to perform 
 * constant time checks of the module's state (like for unit tests). The main 
 * event loop will no longer be run after the 'exit' callback finishes, so 
 * timers may not be scheduled.
 * <p>
 * Example of listening for exit:
 */
process.on('exit', function () {
  process.nextTick(function () {
   console.log('This will not run');
  });
  console.log('About to exit.');
});