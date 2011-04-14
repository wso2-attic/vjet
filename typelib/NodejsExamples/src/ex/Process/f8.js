/**
 * Sets the group identity of the process. (See setgid(2).) This accepts either 
 * a numerical ID or a groupname string. If a groupname is specified, this method 
 * blocks while resolving it to a numerical ID.
 */
console.log('Current gid: ' + process.getgid());
try {
  process.setgid(501);
  console.log('New gid: ' + process.getgid());
}
catch (err) {
  console.log('Failed to set gid: ' + err);
}