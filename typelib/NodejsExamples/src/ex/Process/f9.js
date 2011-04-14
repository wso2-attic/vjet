/**
 * Sets the user identity of the process. (See setuid(2).) This accepts either 
 * a numerical ID or a username string. If a username is specified, this method 
 * blocks while resolving it to a numerical ID.
 */
console.log('Current uid: ' + process.getuid());
try {
  process.setuid(501);
  console.log('New uid: ' + process.getuid());
}
catch (err) {
  console.log('Failed to set uid: ' + err);
}