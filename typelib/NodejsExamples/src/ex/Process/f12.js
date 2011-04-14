/**
 * On the next loop around the event loop call this callback. This is not a 
 * simple alias to setTimeout(fn, 0), it's much more efficient.
 */
process.nextTick(function () {
  console.log('nextTick callback');
});