vjo.etype('BugJsFiles.Bug6474') //< public
.values('Start, Middle, End')
.props({
    // this.Start is undefined at this point
    InitialState: undefined, //< public final   

    main: function(args) {
          var out = vjo.sysout.println ;
          out(this.Middle) ;
          out(this.InitialState) ;
          out(this.InitialState === this.Start) ;
    }
})
.
.endType();