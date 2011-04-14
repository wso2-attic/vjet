var out = vjo.sysout.println ;

function resolver() { //< String resolver(Object...)
    var version = Number(arguments[0]) ;
    var olAsString = arguments[1];
    // We expect {} but need to assign it something so we can use eval() result
    var myol = eval("myol = " + olAsString) ;
    var module = myol.module ;
    var remote = myol.remote ;
    
    var secure = Boolean(arguments[2]) ;
    
    out(version) ; out(module) ;
    out(remote) ;  out(secure) ;
    
    if ('fs' == module) return 'node.js.fs' ;
    if ('net' == module) return 'node.js.net' ;
    
    // We don't recognize the module, contract says return null
    return null ;
}

var typeName = resolver('1.0', "{module: 'net', remote: true}", 'false') ;
out(typeName) ;





