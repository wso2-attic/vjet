vjo.ctype("vjo.java.lang.StringFactory")
//> needs(vjo.java.lang.StringBuffer)
//> needs(vjo.java.lang.StringBuilder)
.needs("vjo.java.lang.Exception")
.props({
	   //> public String build()
     build:function(){
         if(arguments.length===0){
               return this.build_0_0();
         }else if(arguments.length===1){
             if(arguments[0] instanceof Array){
                 if (this.isChar(arguments[0])) {
                	 return this.build_1_0(arguments[0]);
                 } else if (this.isByte(arguments[0])) {
                     return this.build_1_1(arguments[0]);
                 }
             }else if(arguments[0] instanceof String || typeof arguments[0] =="string"){
                 return this.build_1_2(arguments[0]);
             }else if(arguments[0] instanceof vjo.java.lang.StringBuffer || arguments[0] instanceof vjo.java.lang.StringBuilder){
                 return this.build_1_3(arguments[0]);
             }
         }else if(arguments.length===3){
         	 if (this.isChar(arguments[0])) {
         	        return this.build_3_0(arguments[0],arguments[1],arguments[2]);
         	 } else if (this.isByte(arguments[0])) {
         		 return this.build_3_1(arguments[0],arguments[1],arguments[2]);         	 
         	 }          
        }else if(arguments.length===4){
            if(arguments[0] instanceof Array && typeof arguments[1]=="number" && typeof arguments[2]=="number" && typeof arguments[3]=="number"){
                return this.build_4_0(arguments[0],arguments[1],arguments[2],arguments[3]);
            }else if(arguments[0] instanceof Array && typeof arguments[1]=="number" && typeof arguments[2]=="number" && (arguments[3] instanceof String || typeof arguments[3]=="string")){
                return this.build_4_1(arguments[0],arguments[1],arguments[2],arguments[3]);
            }
        }
     },
     //> public String build_0_0()
     build_0_0:function(){
         return new String();
     },
     //> public String build_1_0(char [] a)
     build_1_0:function(a){
     	var s = new String(a.join(""));
     	return s;
     },
     //> public String build_1_0(byte [] a)
     build_1_1:function(a){
        //TODO:
        var s = new String();
     	return s;
     },      
     //> public String build_1_2(String s)
     build_1_2:function(s){
         var str = new String(s);
         return str;
     },
     //> public String build_1_3(StringBuffer s)
     build_1_3:function(s){
         var a = s.shareValue();
         var st = new String(a.join(""));
         return st;
     },
    //> public String build_3_0(char[] data,int start,int len)
     build_3_0:function(data,start,length){
         
        if( start>= 0 &&  0 <=length &&   length<= data.length-start){
        	var s = data.join("");
            	return s;
     	
            }else {
           throw new vjo.java.lang.Exception("Index Out of Bound");
        }
    },
    //> public String build_3_1(byte[] data,int start,int len)
    build_3_1:function(data,start,length){
            if(start>=0&&0<=length && length<=data.length-start){
            //TODO decode
        	var s = new String();
        	return s;
        }else {
           throw new vjo.java.lang.Exception("Index Out of Bound");
        }
    }, 
    //> public String build_4_0(byte[] data,int high,int start,int len)
    build_4_0:function(data,high,start,length){
        if(data!=null){
            if(start>=0&&0<=length && length<=data.length-start){
               var a =new Array(length);
                high<<=8;
                for (var i=0;i<this.count;i++){
                   a[i]=(high+(data[start++]&0xff));
                }
        		var s = new String(data.join(""));
        		return s;                
            }else {
                throw new vjo.java.lang.Exception("Index Out of Bound");
            }
        }else {
            throw new vjo.java.lang.Exception("Null Point Exception");
        }
    },   
    //> public String build_4_1(int[] data,int start,int length,String encoding)
    build_4_1:function(data,start,length,encoding){
        if(encoding==null || encoding =="undefined"){
            throw new vjo.java.lang.Exception("Null Point Exception");
        }
        if(start>=0&&0<=length && length<=data.length-start){	
            //TODO decode
        	var s = new String();
        	return s;
        }else {
            throw new vjo.java.lang.Exception("Index Out of Bound");
        }
    },          
     isChar:function(a) {
     	for (var i = 0; i < a.length;i++) {
     		// allow default array value of '' for a char array
			if (typeof a[i] != "string" || a[i].length > 1) {
				return false;
			}
     	}
     	return true;
     },
     isByte:function(a) {
     	for (var i = 0; i < a.length;i++) {
     	   if (typeof a[i] != "number" || a[i] > 127) {
     	   	return false;
     	   }
     	}
     	return true;
     }
})
.endType();
