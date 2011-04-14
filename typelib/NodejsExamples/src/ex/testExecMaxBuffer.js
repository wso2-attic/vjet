//> needs(org.nodejs.fs.WriteableStream)

var common = require('../common'); 
var exec = require('child_process').exec; 
var assert = require('assert'); 

var cmd = 'echo "hello world"';
 
//> void f(Error, WriteableStream, WriteableStream)
	function arby(err, stdout, stderr) {

		assert.ok(err);
		assert.ok(/maxBuffer/.test(err.message));
	}
	
exec(
	cmd, 
	{ maxBuffer: 5 }, 
	//> void f(Error, WriteableStream, WriteableStream)
	function(err, stdout, stderr) {
		assert.ok(err);
		assert.ok(/maxBuffer/.test(err.message));
	}
);

exec(
	cmd, 
	{ maxBuffer: 5 }, 
	//> void f(Error, org.nodejs.fs.WriteableStream, org.nodejs.fs.WriteableStream)
	function(err, stdout, stderr) {
		assert.ok(err);
		assert.ok(/maxBuffer/.test(err.message));
	}
);
