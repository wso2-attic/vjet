vjo.ctype('vjoPro.example.VjComp') //< public
.needs(['vjoPro.example.Utilities','vjoPro.example.DomUtilities'])
.inherits('vjoPro.example.BaseComp')
.props({
staticMethod : function(){

},

innerFuncTwo : function(){
alert("innerFunctiontwo");

}
})
.protos({

/**
* @return void
* @access public
* @param {String} pHello
*/
//> public constructs(String pHello)
constructs: function(pHello){

alert("constructed with " + pHello);
/**
* @return void
* @access protected
*/
this.innerFuncOne = function(){
alert("innerFunctionOne");

};


},

/**
* @JsClass MySample
* @return boolean
* @access public
* @JsEventHandler
*/
//> public boolean hello()
hello : function(){
this.printArgs(arguments);
return true;
},

/**
* @JsClass MySample
* @return void
* @access public
* @JsEventHandler
*/
//> public void helloTwo()
helloTwo : function(){
this.printArgs(arguments);
},

/**
* @return java.lang.Enum
* @access protected
* @JsArray MyCarsArray
*/
getMyCarsArray : function(){
this.MyCarsArray= [];
this.MyCarsArray.cool="Mustang";
this.MyCarsArray.family="Station Wagon";
this.MyCarsArray.big="SUV";
},

/**
* @JsClass MySample
* @return void
* @access public
* @param {String} testString
* @param {MyCarsArray} testInt
* @JsEventHandler
*/
//> public void helloThree(String testString,Object testInt)
helloThree : function(testString,testInt){
this.printArgs(arguments);
},
/**
* @JsClass MySample
* @return void
* @access public
* @param {java.util.Date} testDate
* @param {boolean} testBool
* @JsEventHandler
*/
//> public void helloFour(Date testDate,boolean testBool)
helloFour : function(testDate,testBool){
this.printArgs(arguments);
},
/**
* @JsClass MySample
* @return void
* @access public
* @param {char} charLow
* @param {char} charHigh
* @param {char} charMid
* @JsEventHandler
*/
//> public void helloChars(char charLow,char charHigh,char charMid)
helloChars : function(charLow,charHigh,charMid){
this.printArgs(arguments);
},
/**
* @JsClass MySample
* @return void
* @access public
* @param {short} shortLow
* @param {short} shortHigh
* @param {short} shortMid
* @JsEventHandler
*/
//> public void helloShort(short shortLow,short shortHigh,short shortMid)
helloShort : function(shortLow,shortHigh,shortMid){
this.printArgs(arguments);
},
/**
* @JsClass MySample
* @return void
* @access public
* @param {int} intLow
* @param {int} intHigh
* @param {int} intMid
* @JsEventHandler
*/
//> public void helloInts(int intLow,int intHigh,int intMid)
helloInts : function(intLow,intHigh,intMid){
this.printArgs(arguments);
},
/**
* @JsClass MySample
* @return void
* @access public
* @param {long} longLow
* @param {long} longHigh
* @param {long} longMid
* @JsEventHandler
*/
//> public void helloLong(long longLow,long longHigh,long longMid)
helloLong : function(longLow,longHigh,longMid){
this.printArgs(arguments);
},
/**
* @JsClass MySample
* @return void
* @access public
* @param {float} floatLow
* @param {float} floatHigh
* @param {float} floatMid
* @JsEventHandler
*/
//> public void helloFloat(float floatLow,float floatHigh,float floatMid)
helloFloat : function(floatLow,floatHigh,floatMid){
this.printArgs(arguments);
},
/**
* @JsClass MySample
* @return void
* @access public
* @param {double} doubleLow
* @param {double} doubleHigh
* @param {double} doubleMid
* @JsEventHandler
*/
//> public void helloDouble(double doubleLow,double doubleHigh,double doubleMid)
helloDouble : function(doubleLow,doubleHigh,doubleMid){
this.printArgs(arguments);
},
/**
* @JsClass MySample
* @return void
* @access public
* @param {vjoPro.example.IPersonJsBeanRef} pPerson
* @JsEventHandler
*/
//> public void helloMyJavaType(Object pPerson)
helloMyJavaType : function(pPerson){
alert("Person : " + pPerson.firstName + "\n" + pPerson.lastName+ "\n" + pPerson.age);
},

/**
* @JsClass MySample
* @return void
* @access public
*/
//> public void printArgs(Object)
printArgs : function(myarguments){

if(myarguments===null){
return;
}
var x = "";
for(var i=0; i<myarguments.length; i++){
x+= myarguments[i].toString() + "\n";
}
alert(x);

}

})
.endType();
