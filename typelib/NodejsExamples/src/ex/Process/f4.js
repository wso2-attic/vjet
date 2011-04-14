/**
 * A Writable Stream to stdout.
 * <p>
 * Example: the definition of console.logx
 */
console.logx = function (d) {
  process.stdout.write(d + '\n');
};