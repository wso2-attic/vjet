vjo.ctype('BugJsFiles.Bug9096') //< public
.needs('BugJsFiles.BasejsFile')
.protos({
	E:null,  
	
	E1: BugJsFiles.BasejsFile.sampleJsMethod, //<Function

    //>public constructs()
    constructs : function(){
            this.E = null;
    },

    //>public void func() 
    func : function(){
            var s = this.E("fd");  
            var s1 = this.E1();
    }

})
.endType();
