vjo.ctype("vjoPro.dsf.utils.Array")
.props({
/**
* Creates a new instance of the Array with the same elements from the given
* array.
*
* @param {Object} srcArr
*        The src array
* @return {Object}
*        The new Array with the same elements from the src array
*/
//> public Object[] copy(Object[]);
copy : function(poSrcArr) {
var ra = [];
var i = 0;
for ( i in poSrcArr){
ra[i] = poSrcArr[i];
}
return ra;
},

/**
* Removes an element in the array given its index or its value.
*
* @param {Object} srcArr
*        The src array
* @param {int} index
*        The index value to be removed
* @param {String} value
*        The element value to be removed
* @return {Object}
*        The new array instance without removed element
*/
//> public Object[] remove(Object[],int,String);
remove : function(poSrcArr,piIndex,psValue)	{
var ra = [];
var i = 0;
for (i in poSrcArr){
if (psValue !== null){
if (poSrcArr[i] != psValue){
ra[i] = poSrcArr[i];
}
}
else if (i != piIndex){
ra[i] = poSrcArr[i];
}
}
return ra;
},

/**
* Inserts a new value into the array at the specified position.
*
* @param {Object} srcArr
*        The src array
* @param {int} index
*        The index value of the postion to insert
* @param {String} value
*        A value to be inserted
* @return {Object}
*        The new array instance with newly inserted value
*/
//> public Object[] insert(Object[],int,String);
insert : function(poSrcArr, piIndex, psValue) {
var ra = poSrcArr.splice(piIndex, 0, psValue);
return ra;
},

/**
* Romves the element from the array at the specified position.
*
* @param {Object} srcArr
*        The src array
* @param {int} index
*        The index value of the postion to remove
* @return {Object}
*        The new array instance without removed value
*/
// >public Object[] shift(Object[],int);
shift : function(poSrcArr, piIndex)	{

if (!piIndex){
return poSrcArr.shift();
}
else{
var i=0,
len = poSrcArr.length,
ra = [];
for (i; i<len; i++)
{
if (i != piIndex){
ra[ra.length] = poSrcArr[i];
}
}
return ra;
}
},
//> public boolean contains(Object[] poSrcArr, Object poObject);
contains : function (poSrcArr, poObject) {
if(typeof(poObject)!="undefined" && typeof(poSrcArr.length)!="undefined"){
var l = poSrcArr.length, i;
if(poObject==null){
for (i=0;i<l;i++){
if(poSrcArr[i]==null){
return true;
}
}
}else {
for (i=0;i<l;i++){
if(this.isEquals(poObject,poSrcArr[i])){
return true;
}
}
}
}
return false;
},
isEquals : function(poSource, poTarget){
var type = typeof(poSource);
if(type=="object" && typeof(poSource.equals)=="function"){
return poSource.equals(poTarget);
}else {
return (poSource==poTarget && typeof(poSource)==typeof(poTarget));
}
}
})
.endType();

