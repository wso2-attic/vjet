
vjo.ctype('vjo.java.util.Arrays') //< public
.needs(['vjo.java.lang.Double','vjo.java.lang.Float','vjo.java.lang.Integer',
    'vjo.java.lang.System','vjo.java.lang.IllegalArgumentException','vjo.java.util.HashSet',
    'vjo.java.lang.ArrayIndexOutOfBoundsException','vjo.java.lang.NullPointerException',
    'vjo.java.util.AbstractList','vjo.java.util.RandomAccess','vjo.java.io.Serializable',
    'vjo.java.lang.ObjectUtil','vjo.java.lang.Util','vjo.java.lang.StringBuffer'])
.needs('vjo.java.util.Comparator','')
.needs('vjo.java.util.List','')
.needs('vjo.java.util.Set','')
.needs('vjo.java.lang.Math','')
.needs('vjo.java.lang.reflect.Array','')
.props({
    INSERTIONSORT_THRESHOLD:7, //< private final int
    ArrayList:vjo.ctype() //< public ArrayList<E>
    .inherits('vjo.java.util.AbstractList<E>')
    .satisfies('vjo.java.util.RandomAccess')
    .satisfies('vjo.java.io.Serializable')
    .props({
        serialVersionUID:-2764017481108945198 //< private final long
    })
    .protos({
        a:null, //< private Object[]
        //> constructs(E[] array)
        constructs:function(array){
            this.base();
            if(array===null){
                throw new this.vj$.NullPointerException();
            }
            this.a=array;
        },
        //> public int size()
        size:function(){
            return this.a.length;
        },
        //> public Object[] toArray()
        toArray:function(){
            return this.a.clone();
        },
        //> public E get(int index)
        get:function(index){
            return this.a[index];
        },
        //> public E set(int index,E element)
        set:function(index,element){
            var oldValue=this.a[index];
            this.a[index]=element;
            return oldValue;
        },
        //> public int indexOf(Object o)
        indexOf:function(o){
            if(o===null){
                for (var i=0;i<this.a.length;i++){
                    if(this.a[i]===null){
                        return i;
                    }
                }
            }else {
                for (var i=0;i<this.a.length;i++){
                    if(o.equals(this.a[i])){
                        return i;
                    }
                }
            }
            return -1;
        },
        //> public boolean contains(Object o)
        contains:function(o){
            return this.indexOf(o)!== -1;
        }
    })
    .endType(),
    //> public void sort(long[] a)
    //> public void sort(long[] a,int fromIndex,int toIndex)
    //> public void sort(int[] a)
    //> public void sort(int[] a,int fromIndex,int toIndex)
    //> public void sort(short[] a)
    //> public void sort(short[] a,int fromIndex,int toIndex)
    //> public void sort(char[] a)
    //> public void sort(char[] a,int fromIndex,int toIndex)
    //> public void sort(byte[] a)
    //> public void sort(byte[] a,int fromIndex,int toIndex)
    //> public void sort(double[] a)
    //> public void sort(double[] a,int fromIndex,int toIndex)
    //> public void sort(float[] a)
    //> public void sort(float[] a,int fromIndex,int toIndex)
    //> public void sort(Object[] a)
    //> public void sort(Object[] a,int fromIndex,int toIndex)
    //> public <T> void sort(T[] a,Comparator<? super T> c)
    //> public <T> void sort(T[] a,int fromIndex,int toIndex,Comparator<? super T> c)
    sort:function(a){
        if(arguments.length===1){
            if(arguments[0] instanceof Array){
            	if(a.length > 0){
            		if(typeof a[0] == "number"){
            			this.sort1(a,0,a.length);
            		}else{
	            		var aux=this.cloneSubarray(a,0,a.length);
	                    this.mergeSort(aux,a,0,a.length,0);
            		}
            	}
            } else if(a === null) {
                throw new this.vj$.NullPointerException();
            }
        }else if(arguments.length===3){
            if(arguments[0] instanceof Array && typeof arguments[1]=="number" && typeof arguments[2]=="number"){
            	var fromIndex = arguments[1],toIndex = arguments[2];
	            if(a.length > 0){
            		if(typeof a[0] == "number"){
		           	 	this.rangeCheck(a.length,fromIndex,toIndex);
			            this.sort1(a,fromIndex,toIndex-fromIndex);
	            	}else{
						this.rangeCheck(a.length,fromIndex,toIndex);
						var aux=this.cloneSubarray(a,fromIndex,toIndex);
						this.mergeSort(aux,a,fromIndex,toIndex,-fromIndex);
					}
	            }
            }
        }else if(arguments.length===2){
        	var c = arguments[1];
	        var aux=this.cloneSubarray(a,0,a.length);
	        if(c===null){
	            this.mergeSort(aux,a,0,a.length,0);
	        }else {
	            this.mergeSort(aux,a,0,a.length,0,c);
	        }
        }else if(arguments.length===4){
        	var fromIndex = arguments[1],toIndex = arguments[2],c = arguments[3];
	        this.rangeCheck(a.length,fromIndex,toIndex);
	        var aux=this.cloneSubarray(a,fromIndex,toIndex);
	        if(c===null){
	            this.mergeSort(aux,a,fromIndex,toIndex,-fromIndex);
	        }else {
	            this.mergeSort(aux,a,fromIndex,toIndex,-fromIndex,c);
	        }
        }
    },
    //> private void sort1(long[] x,int off,int len)
    //> private void sort1(int[] x,int off,int len)
    //> private void sort1(short[] x,int off,int len)
    //> private void sort1(char[] x,int off,int len)
    //> private void sort1(byte[] x,int off,int len)
    //> private void sort1(double[] x,int off,int len)
    //> private void sort1(float[] x,int off,int len)
    sort1:function(x,off,len){
        if(arguments.length===3){
            if(arguments[0] instanceof Array && typeof arguments[1]=="number" && typeof arguments[2]=="number"){
                if(len<7){
		            for (var i=off;i<len+off;i++){
		                for (var j=i;j>off && x[j-1]>x[j];j--){
		                    this.swap(x,j,j-1);
		                }
		            }
		            return;
		        }
		        var m=off+(len>>1);
		        if(len>7){
		            var l=off;
		            var n=off+len-1;
		            if(len>40){
		                var s=parseInt(len/8);
		                l=this.med3(x,l,l+s,l+2*s);
		                m=this.med3(x,m-s,m,m+s);
		                n=this.med3(x,n-2*s,n-s,n);
		            }
		            m=this.med3(x,l,m,n);
		        }
		        var v=x[m];
		        var a=off,b=a,c=off+len-1,d=c;
		        while(true){
		            while(b<=c && x[b]<=v){
		                if(x[b]===v){
		                    this.swap(x,a++,b);
		                }
		                b++;
		            }
		            while(c>=b && x[c]>=v){
		                if(x[c]===v){
		                    this.swap(x,c,d--);
		                }
		                c--;
		            }
		            if(b>c){
		                break;
		            }
		            this.swap(x,b++,c--);
		        }
		        var s,n=off+len;
		        s=Math.min(a-off,b-a);
		        this.vecswap(x,off,b-s,s);
		        s=Math.min(d-c,n-d-1);
		        this.vecswap(x,b,n-s,s);
		        if((s=b-a)>1){
		            this.sort1(x,off,s);
		        }
		        if((s=d-c)>1){
		            this.sort1(x,n-s,s);
		        }
            }
        }
    },
    //> private void swap(long[] x,int a,int b)
    //> private void swap(int[] x,int a,int b)
    //> private void swap(short[] x,int a,int b)
    //> private void swap(char[] x,int a,int b)
    //> private void swap(byte[] x,int a,int b)
    //> private void swap(double[] x,int a,int b)
    //> private void swap(float[] x,int a,int b)
    //> private void swap(Object[] x,int a,int b)
    swap:function(x,a,b){
        if(arguments.length===3){
            if(arguments[0] instanceof Array && typeof arguments[1]=="number" && typeof arguments[2]=="number"){
	            var t=x[a];
	            x[a]=x[b];
	            x[b]=t;
            }
        }
    },
    //> private void vecswap(long[] x,int a,int b,int n)
    //> private void vecswap(int[] x,int a,int b,int n)
    //> private void vecswap(short[] x,int a,int b,int n)
    //> private void vecswap(char[] x,int a,int b,int n)
    //> private void vecswap(byte[] x,int a,int b,int n)
    //> private void vecswap(double[] x,int a,int b,int n)
    //> private void vecswap(float[] x,int a,int b,int n)
    vecswap:function(x,a,b,n){
        if(arguments.length===4){
            if(arguments[0] instanceof Array && typeof arguments[1]=="number" && typeof arguments[2]=="number" && typeof arguments[3]=="number"){
            	for (var i=0; i<n; i++, a++, b++)
    	    		this.swap(x, a, b);
            }
        }
    },
    //> private int med3(long[] x,int a,int b,int c)
    //> private int med3(int[] x,int a,int b,int c)
    //> private int med3(short[] x,int a,int b,int c)
    //> private int med3(char[] x,int a,int b,int c)
    //> private int med3(byte[] x,int a,int b,int c)
    //> private int med3(double[] x,int a,int b,int c)
    //> private int med3(float[] x,int a,int b,int c)
    med3:function(x,a,b,c){
        if(arguments.length===4){
            if(arguments[0] instanceof Array && typeof arguments[1]=="number" && typeof arguments[2]=="number" && typeof arguments[3]=="number"){
            	return (x[a]<x[b]?(x[b]<x[c]?b:x[a]<x[c]?c:a):(x[b]>x[c]?b:x[a]>x[c]?c:a));
            }
        }
    },
    //> private <T> T[] cloneSubarray(T[] a,int from,int to)
    cloneSubarray:function(a,from,to){
        var n=to-from;
//        var result=vjo.java.lang.reflect.Array.newInstance(a.getClass().getComponentType(),n);
        var result=vjo.java.lang.reflect.Array.newInstance(null,n); //refer to Array.js, arg[0] will be discarded.
        this.vj$.System.arraycopy(a,from,result,0,n);
        return result;
    },
    //> private void mergeSort(Object[] src,Object[] dest,int low,int high,int off)
    //> private void mergeSort(Object[] src,Object[] dest,int low,int high,int off,Comparator c)
    mergeSort:function(src,dest,low,high,off){
        if(arguments.length===5){
            this.vj$.Arrays.mergeSort_5_0_Arrays_ovld(arguments[0],arguments[1],arguments[2],arguments[3],arguments[4]);
        }else if(arguments.length===6){
            this.vj$.Arrays.mergeSort_6_0_Arrays_ovld(arguments[0],arguments[1],arguments[2],arguments[3],arguments[4],arguments[5]);
        }
    },
    //> private void mergeSort_5_0_Arrays_ovld(Object[] src,Object[] dest,int low,int high,int off)
    mergeSort_5_0_Arrays_ovld:function(src,dest,low,high,off){
        var length=high-low;
        if(length<this.INSERTIONSORT_THRESHOLD){
            for (var i=low;i<high;i++){
                for (var j=i;j>low && vjo.java.lang.ObjectUtil.compareTo((dest[j-1]),dest[j])>0;j--){
                    this.swap(dest,j,j-1);
                }
            }
            return;
        }
        var destLow=low;
        var destHigh=high;
        low+=off;
        high+=off;
        var mid=(low+high)>>1;
        this.mergeSort(dest,src,low,mid,-off);
        this.mergeSort(dest,src,mid,high,-off);
        if(vjo.java.lang.ObjectUtil.compareTo((src[mid-1]),src[mid])<=0){
            this.vj$.System.arraycopy(src,low,dest,destLow,length);
            return;
        }
        for (var i=destLow,p=low,q=mid;i<destHigh;i++){
            if(q>=high||p<mid&&vjo.java.lang.ObjectUtil.compareTo((src[p]),src[q])<=0){
                dest[i]=src[p++];
            }else {
                dest[i]=src[q++];
            }
        }
    },
    //> private void mergeSort_6_0_Arrays_ovld(Object[] src,Object[] dest,int low,int high,int off,Comparator c)
    mergeSort_6_0_Arrays_ovld:function(src,dest,low,high,off,c){
        var length=high-low;
        if(length<this.INSERTIONSORT_THRESHOLD){
            for (var i=low;i<high;i++){
                for (var j=i;j>low && c.compare(dest[j-1],dest[j])>0;j--){
                    this.swap(dest,j,j-1);
                }
            }
            return;
        }
        var destLow=low;
        var destHigh=high;
        low+=off;
        high+=off;
        var mid=(low+high)>>1;
        this.mergeSort(dest,src,low,mid,-off,c);
        this.mergeSort(dest,src,mid,high,-off,c);
        if(c.compare(src[mid-1],src[mid])<=0){
            this.vj$.System.arraycopy(src,low,dest,destLow,length);
            return;
        }
        for (var i=destLow,p=low,q=mid;i<destHigh;i++){
            if(q>=high||p<mid&&c.compare(src[p],src[q])<=0){
                dest[i]=src[p++];
            }else {
                dest[i]=src[q++];
            }
        }
    },
    //> private void rangeCheck(int arrayLen,int fromIndex,int toIndex)
    rangeCheck:function(arrayLen,fromIndex,toIndex){
        if(fromIndex>toIndex){
            throw new this.vj$.IllegalArgumentException("fromIndex("+fromIndex+") > toIndex("+toIndex+")");
        }
        if(fromIndex<0){
            throw new this.vj$.ArrayIndexOutOfBoundsException(fromIndex);
        }
        if(toIndex>arrayLen){
            throw new this.vj$.ArrayIndexOutOfBoundsException(toIndex);
        }
    },
    //> public int binarySearch(long[] a,long key)
    //> public int binarySearch(int[] a,int key)
    //> public int binarySearch(short[] a,short key)
    //> public int binarySearch(char[] a,char key)
    //> public int binarySearch(byte[] a,byte key)
    //> public int binarySearch(double[] a,double key)
    //> private int binarySearch(double[] a,double key,int low,int high)
    //> public int binarySearch(float[] a,float key)
    //> private int binarySearch(float[] a,float key,int low,int high)
    //> public int binarySearch(Object[] a,Object key)
    //> public <T> int binarySearch(T[] a,T key,Comparator<? super T> c)
    binarySearch:function(a,key){
        if(arguments.length===2){
            if(arguments[0] instanceof Array){
                return this.vj$.Arrays.binarySearch_2_0_Arrays_ovld(arguments[0],arguments[1]);
            }
        }else if(arguments.length===4){
            if(arguments[0] instanceof Array && typeof arguments[1]=="number" && typeof arguments[2]=="number" && typeof arguments[3]=="number"){
                return this.vj$.Arrays.binarySearch_4_0_Arrays_ovld(arguments[0],arguments[1],arguments[2],arguments[3]);
            }
        }else if(arguments.length===3){
            return this.vj$.Arrays.binarySearch_3_0_Arrays_ovld(arguments[0],arguments[1],arguments[2]);
        }
    },
    //> private int binarySearch_2_0_Arrays_ovld(long[] a,long key)
    //> private int binarySearch_2_0_Arrays_ovld(int[] a,int key)
    //> private int binarySearch_2_0_Arrays_ovld(short[] a,short key)
    //> private int binarySearch_2_0_Arrays_ovld(byte[] a,byte key)
    //> private int binarySearch_2_0_Arrays_ovld(char[] a,char key)
    //> private int binarySearch_2_0_Arrays_ovld(float[] a,float key)
    //> private int binarySearch_2_0_Arrays_ovld(double[] a,double key)
    //> private int binarySearch_2_0_Arrays_ovld(Object[] a,Object key)
    binarySearch_2_0_Arrays_ovld:function(a,key){
        var low=0;
        var high=a.length-1;
        while(low<=high){
            var mid=(low+high)>>1;
            var midVal=a[mid];
            var comparison = this.vj$.ObjectUtil.compareTo(midVal, key);
            if(comparison<0){
                low=mid+1;
           }else if(comparison>0){
                high=mid-1;
            }else {
                return mid;
            }
        }
        return -(low+1);
    },
    //> private int binarySearch_4_0_Arrays_ovld(double[] a,double key,int low,int high)
    //> private int binarySearch_4_0_Arrays_ovld(float[] a,float key,int low,int high)  
    binarySearch_4_0_Arrays_ovld:function(a,key,low,high){
        while(low<=high){
            var mid=(low+high)>>1;
            var midVal=a[mid];
            var cmp;
            if(midVal<key){
                cmp=-1;
            }else if(midVal>key){
                cmp=1;
            }else {
                var midBits=midVal;
                var keyBits=key;
                cmp=(midBits===keyBits?0:(midBits<keyBits?-1:1));
            }
            if(cmp<0){
                low=mid+1;
            }else if(cmp>0){
                high=mid-1;
            }else {
                return mid;
            }
        }
        return -(low+1);
    },
//    //> private int binarySearch_4_0_Arrays_ovld(double[] a,double key,int low,int high)
//    binarySearch_4_0_Arrays_ovld:function(a,key,low,high){
//        while(low<=high){
//            var mid=(low+high)>>1;
//            var midVal=a[mid];
//            var cmp;
//            if(midVal<key){
//                cmp=-1;
//            }else if(midVal>key){
//                cmp=1;
//            }else {
//                var midBits=this.vj$.Double.doubleToLongBits(midVal);
//                var keyBits=this.vj$.Double.doubleToLongBits(key);
//                cmp=(midBits===keyBits?0:(midBits<keyBits?-1:1));
//            }
//            if(cmp<0){
//                low=mid+1;
//            }else if(cmp>0){
//                high=mid-1;
//            }else {
//                return mid;
//            }
//        }
//        return -(low+1);
//    },
//    //> private int binarySearch_4_1_Arrays_ovld(float[] a,float key,int low,int high)
//    binarySearch_4_1_Arrays_ovld:function(a,key,low,high){
//        while(low<=high){
//            var mid=(low+high)>>1;
//            var midVal=a[mid];
//            var cmp;
//            if(midVal<key){
//                cmp=-1;
//            }else if(midVal>key){
//                cmp=1;
//            }else {
//                var midBits=this.vj$.Float.floatToIntBits(midVal);
//                var keyBits=this.vj$.Float.floatToIntBits(key);
//                cmp=(midBits===keyBits?0:(midBits<keyBits?-1:1));
//            }
//            if(cmp<0){
//                low=mid+1;
//            }else if(cmp>0){
//                high=mid-1;
//            }else {
//                return mid;
//            }
//        }
//        return -(low+1);
//    },
    //> private <T> int binarySearch_3_0_Arrays_ovld(T[] a,T key,Comparator<? super T> c)
    binarySearch_3_0_Arrays_ovld:function(a,key,c){
        if(c===null){
            return this.binarySearch(a,key);
        }
        var low=0;
        var high=a.length-1;
        while(low<=high){
            var mid=(low+high)>>1;
            var midVal=a[mid];
            var cmp=c.compare(midVal,key);
            if(cmp<0){
                low=mid+1;
            }else if(cmp>0){
                high=mid-1;
            }else {
                return mid;
            }
        }
        return -(low+1);
    },
    //> public boolean equals(long[] a,long[] a2)
    //> public boolean equals(int[] a,int[] a2)
    //> public boolean equals(short[] a,short[] a2)
    //> public boolean equals(char[] a,char[] a2)
    //> public boolean equals(byte[] a,byte[] a2)
    //> public boolean equals(boolean[] a,boolean[] a2)
    //> public boolean equals(double[] a,double[] a2)
    //> public boolean equals(float[] a,float[] a2)
    //> public boolean equals(Object[] a,Object[] a2)
    equals:function(a,a2){
        if(arguments.length===2){
            if(arguments[0] instanceof Array && arguments[1] instanceof Array){
                if(a===a2){
		            return true;
		        }
		        if(a===null||a2===null){
		            return false;
		        }
		        var length=a.length;
		        if(a2.length!==length){
		            return false;
		        }
		        for (var i=0;i<length;i++){
		            if(a[i]!==a2[i]){
		                return false;
		            }
		        }
		        return true;
		    }
	    }
        return false;
    },
    //> public void fill(long[] a,long val)
    //> public void fill(long[] a,int fromIndex,int toIndex,long val)
    //> public void fill(int[] a,int val)
    //> public void fill(int[] a,int fromIndex,int toIndex,int val)
    //> public void fill(short[] a,short val)
    //> public void fill(short[] a,int fromIndex,int toIndex,short val)
    //> public void fill(char[] a,char val)
    //> public void fill(char[] a,int fromIndex,int toIndex,char val)
    //> public void fill(byte[] a,byte val)
    //> public void fill(byte[] a,int fromIndex,int toIndex,byte val)
    //> public void fill(boolean[] a,boolean val)
    //> public void fill(boolean[] a,int fromIndex,int toIndex,boolean val)
    //> public void fill(double[] a,double val)
    //> public void fill(double[] a,int fromIndex,int toIndex,double val)
    //> public void fill(float[] a,float val)
    //> public void fill(float[] a,int fromIndex,int toIndex,float val)
    //> public void fill(Object[] a,Object val)
    //> public void fill(Object[] a,int fromIndex,int toIndex,Object val)
    fill:function(a,val){
        if(arguments.length===2){
            if(arguments[0] instanceof Array){
                this.vj$.Arrays.fill_2_0_Arrays_ovld(arguments[0],arguments[1]);
            }
        }else if(arguments.length===4){
            if(arguments[0] instanceof Array && typeof arguments[1]=="number" && typeof arguments[2]=="number"){
                this.vj$.Arrays.fill_4_0_Arrays_ovld(arguments[0],arguments[1],arguments[2],arguments[3]);
            }
        }
    },
    //> public void fill_2_0_Arrays_ovld(long[] a,long val)
    //> public void fill_2_0_Arrays_ovld(int[] a,int val)
    //> public void fill_2_0_Arrays_ovld(short[] a,short val)
    //> public void fill_2_0_Arrays_ovld(char[] a,char val)
    //> public void fill_2_0_Arrays_ovld(byte[] a,byte val)
    //> public void fill_2_0_Arrays_ovld(boolean[] a,boolean val)
    //> public void fill_2_0_Arrays_ovld(double[] a,double val)
    //> public void fill_2_0_Arrays_ovld(float[] a,float val)
    //> public void fill_2_0_Arrays_ovld(Object[] a,Object val)
    fill_2_0_Arrays_ovld:function(a,val){
        this.fill(a,0,a.length,val);
    },
    //> public void fill_4_0_Arrays_ovld(long[] a,int fromIndex,int toIndex,long val)
    //> public void fill_4_0_Arrays_ovld(int[] a,int fromIndex,int toIndex,int val)
    //> public void fill_4_0_Arrays_ovld(short[] a,int fromIndex,int toIndex,short val)
    //> public void fill_4_0_Arrays_ovld(char[] a,int fromIndex,int toIndex,char val)
    //> public void fill_4_0_Arrays_ovld(byte[] a,int fromIndex,int toIndex,byte val)
    //> public void fill_4_0_Arrays_ovld(boolean[] a,int fromIndex,int toIndex,boolean val)
    //> public void fill_4_0_Arrays_ovld(double[] a,int fromIndex,int toIndex,double val)
    //> public void fill_4_0_Arrays_ovld(float[] a,int fromIndex,int toIndex,float val)
    //> public void fill_4_0_Arrays_ovld(Object[] a,int fromIndex,int toIndex,Object val)
    fill_4_0_Arrays_ovld:function(a,fromIndex,toIndex,val){
        this.rangeCheck(a.length,fromIndex,toIndex);
        for (var i=fromIndex;i<toIndex;i++){
            a[i]=val;
        }
    },    
    //> public <T> List<T> asList(T... a)
    asList:function(){
        var a;
        var arg = arguments;
        if (arguments.length == 1){
    		if(arguments[0]  instanceof Array || arguments[0] == null){
            	a=arguments[0];
			}
        }
        else {
            a=[];
            for (var i=0; i<arguments.length; i++){
                a[i-0]=arguments[i];
            }
        }
        return new this.ArrayList(a);
    },
    //> public int hashCode(long[] a)
    //> public int hashCode(int[] a)
    //> public int hashCode(short[] a)
    //> public int hashCode(char[] a)
    //> public int hashCode(byte[] a)
    //> public int hashCode(boolean[] a)
    //> public int hashCode(float[] a)
    //> public int hashCode(double[] a)
    //> public int hashCode(Object[] a)
    hashCode:function(a){
        if(arguments.length===1){
	        if(a===null){
		        return 0;
		    }
		    var result=1;
		    for (var element,_$i=0;_$i<a.length;_$i++){
		        element=a[_$i];
		        var elementHash=this.vj$.ObjectUtil.hashCode(element);
		        result=31*result+elementHash;
		    }
		    return result;
        }
    },
    //> public int deepHashCode(Object[] a)
    deepHashCode:function(a){
        if(a===null){
            return 0;
        }
        var result=1;
		for (var element,_$i=0;_$i<a.length;_$i++){
			element=a[_$i];
			var elementHash=0;
			if(element instanceof Array){
				elementHash=this.vj$.Arrays.deepHashCode(element);
			}else{
				elementHash = this.vj$.ObjectUtil.hashCode(element);
			}
			result=31*result+elementHash;
		}

        return result;
    },
    //> public boolean deepEquals(Object[] a1,Object[] a2)
    deepEquals:function(a1,a2){
        if(a1===a2){
            return true;
        }
        if(a1===null||a2===null){
            return false;
        }
        var length=a1.length;
        if(a2.length!==length){
            return false;
        }
        for (var i=0;i<length;i++){
            var e1=a1[i];
            var e2=a2[i];
            if(e1===e2){
                continue;
            }
            if(e1===null){
                return false;
            }
            var eq;
            if(e1 instanceof Array && e2 instanceof Array){
            	eq = this.vj$.Arrays.deepEquals(e1, e2);
            }else{
            	eq = this.vj$.ObjectUtil.equals(e1, e2);
            }
            if(!eq){
                return false;
            }
        }
        return true;
    },
    //> public String toString(boolean[] a)
    //> public String toString(byte[] a)
    //> public String toString(char[] a)
    //> public String toString(short[] a)
    //> public String toString(int[] a)
    //> public String toString(long[] a)
    //> public String toString(float[] a)
    //> public String toString(double[] a)
    //> public String toString(Object[] a)
    toString:function(a){
        if (a === null)
            return "null";
        if (a.length === 0)
            return "[]";

        var buf = new this.vj$.StringBuffer();
        buf.append('[');
        buf.append(a[0]);

        for (var i = 1; i < a.length; i++) {
            buf.append(", ");
            buf.append(a[i]);
        }

        buf.append("]");
        return buf.toString();
    },
    //> public String deepToString(Object[] a)
    deepToString:function(a){
        if (a === null)
            return "null";

        //var bufLen = 20 * a.length;
        //if (a.length != 0 && bufLen <= 0)
        //    bufLen = this.vj$.Integer.MAX_VALUE;
        var buf = new this.vj$.StringBuffer();
        this.deepToStringRecursively(a, buf, new this.vj$.HashSet());
        return buf.toString();
    },
    //> private void deepToStringRecursively(Object[] a,StringBuffer buf,vjo.java.util.Set<Object[]> dejaVu)
    deepToStringRecursively:function(a,buf,dejaVu){
        if (a === null) {
            buf.append("null");
            return;
        }
        dejaVu.add(a);
        buf.append('[');
        for (var i = 0; i < a.length; i++) {
            if (i != 0)
                buf.append(", ");

            var element = a[i];
            if (element === null) {
                buf.append("null");
            } else {
               if (vjo.isArray(element)) {
                    if (dejaVu.contains(element))
                        buf.append("[...]");
                    else
                        this.deepToStringRecursively(element, buf, dejaVu);
                } else {  // element is non-null and not an array
                    buf.append(element.toString());
                }
            }
        }
        buf.append("]");
        dejaVu.remove(a);
    }
})
.protos({
    //> private constructs()
    constructs:function(){
    }
})
.endType();