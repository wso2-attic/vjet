vjo.ctype('debug.SimpleMain1') //< public
.props({
    //> public void main(String[] args)
    main:function(args){
        var a = 'Hello'; //<String
        var b = 'World'; //<String
        var c = a + ' ' + b;
        vjo.sysout.println(c);
    }
})
.inits(function(){
	
})
.endType();