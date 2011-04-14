/**
 * Sets or reads the process's file mode creation mask. Child processes inherit 
 * the mask from the parent process. Returns the old mask if mask argument is 
 * given, otherwise returns the current mask.
 */
var oldmask = 0; 
var newmask = 0644;

oldmask = process.umask(newmask);
console.log('Changed umask from: ' + oldmask.toString(8) +
            ' to ' + newmask.toString(8));