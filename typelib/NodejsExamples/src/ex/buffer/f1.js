/**
 * For Unix domain datagram sockets, the destination address is a pathname in 
 * the filesystem. An optional callback may be supplied that is invoked after 
 * the sendto call is completed by the OS. It is not safe to re-use buf until 
 * the callback is invoked. Note that unless the socket is bound to a pathname 
 * with bind() there is no way to receive messages on this socket.
 * <p>Example of sending a message to syslogd on OSX via Unix domain socket 
 * /var/run/syslog:
 */
var dgram = require('dgram');
var message = new Buffer("A message to log.");
var client = dgram.createSocket("unix_dgram");
client.send(message, 0, message.length, "/var/run/syslog",
  function (err, bytes) {
    if (err) {
      throw err;
    }
    console.log("Wrote " + bytes + " bytes to socket.");
});