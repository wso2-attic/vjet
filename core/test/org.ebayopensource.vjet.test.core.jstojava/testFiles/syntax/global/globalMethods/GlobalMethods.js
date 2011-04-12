vjo.ctype('syntax.global.globalMethods.GlobalMethods') //< public
.props({
    //> public void main(String... args)
    main:function(){
        vjo.sysout.println(decodeURI("String"));
        vjo.sysout.println(decodeURIComponent("String"));
        vjo.sysout.println(encodeURI("String"));
        vjo.sysout.println(encodeURIComponent("String"));
        vjo.sysout.println(escape("String"));
        vjo.sysout.println(isFinite(30));
        vjo.sysout.println(isNaN("String"));
        vjo.sysout.println(parseFloat("22.3"));
        vjo.sysout.println(parseInt("22"));
        vjo.sysout.println(unescape("22"));
        vjo.sysout.println(typeof "GlobalMethods");
        vjo.sysout.println(eval("GlobalMethods"));
    }
})
.endType();