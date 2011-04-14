/**
 * Emitted when the processes receives a signal. See sigaction(2) for a list of 
 * standard POSIX signal names such as SIGINT, SIGUSR1, etc.
 * <p>
 * Example of listening for SIGINT:
 */
// Start reading from stdin so we don't exit.
process.stdin.resume();
process.on('SIGINT', function () {
  console.log('Got SIGINT.  Press Control-D to exit.');
});