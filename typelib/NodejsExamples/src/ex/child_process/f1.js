var sys = require('sys');
var fs = require('fs');
var child = require("child_process");

/* Create file streams for stdout/stderr. */
outStream = fs.createWriteStream("./stdout"); 
errStream = fs.createWriteStream("./stderr");

var proc = child.spawn("ls", [], {
  "customFds":[-1, outStream, errStream],
  "env":{
    "NODE_PATH": process.env["NODE_PATH"],
    "SHELL":"/bin/bash",
    "PATH": process.env["PATH"]
  },
  "cwd":"/tmp"
});

proc.addListener("exit", function(code, signal){
  if(signal){
    /* Killed by signal. */
    sys.debug("KILLED BY SIGNAL");
  }
  else if(code){
    /* Process exited with error. */
    sys.debug("CODE WAS " + code);
  }
  else{
    /* Successful termination. */
    sys.debug("SUCCESS");
  }
});
