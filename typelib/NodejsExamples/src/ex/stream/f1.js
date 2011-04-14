/**
 * Emulating the Unix cat command:
 */
process.stdin.resume();
process.stdin.pipe(process.stdout);