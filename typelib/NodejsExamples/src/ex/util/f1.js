var util = require('util') ;

/**
 * A synchronous output function. Will block the process and output string 
 * immediately to stderr.
 */
util.debug('message on stderr');

/**
 * Output with timestamp on stdout.
 */
util.log('Timestmaped message.');

