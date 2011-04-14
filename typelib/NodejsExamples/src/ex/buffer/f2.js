/**
 * For UDP sockets, the destination port and IP address must be specified. A 
 * string may be supplied for the address parameter, and it will be resolved 
 * with DNS. An optional callback may be specified to detect any DNS errors and 
 * when buf may be re-used. Note that DNS lookups will delay the time that a 
 * send takes place, at least until the next tick. The only way to know for sure 
 * that a send has taken place is to use the callback.
 * <p>Example of sending a UDP packet to a random port on localhost;
 */
var dgram = require('dgram');
var message = new Buffer("Some bytes");
var client = dgram.createSocket("udp4");
client.send(message, 0, message.length, 41234, "localhost");
client.close();

