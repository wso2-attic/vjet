/**
 * A Readable Stream for stdin. The stdin stream is paused by default, so one 
 * must call process.stdin.resume() to read from it.
 * <p>
 * Example of opening standard input and listening for both events:
 */
process.stdin.resume();
process.stdin.setEncoding('utf8');

process.stdin.on('data', function (chunk) {
  process.stdout.write('data: ' + chunk);
});

process.stdin.on('end', function () {
  process.stdout.write('end');
});