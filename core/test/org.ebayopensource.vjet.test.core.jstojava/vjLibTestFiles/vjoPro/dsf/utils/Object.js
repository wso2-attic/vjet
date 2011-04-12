vjo.ctype("vjoPro.dsf.utils.Object").
/**
* Provides OO features for the general JS objects.
*/
props({
/**
* Hitches a method to a specified JS object.
*
* @param {JsObj} control
*        the reference of the JS object
* @param {String} method
*        the name of the method
* @return {JsObj}
*        the reference of the wrapped method
*/
//> public JsObj hitch(::Object,String);
hitch : function(poControl, psMethod) {
var fcn;
if(typeof psMethod == "string")
{
fcn = poControl[psMethod];
} else
{
fcn = psMethod;
}
return function(){return fcn.apply(poControl, arguments);};
},

/**
* Builds the inheritance hierarchy between two classes.
*
* @param {Object} subClass
*        the class which acts as the sub class
* @param {Object} baseClass
*        the class which acts as the base class
*
*/
//>public void extend(Function, Function);
extend : function(psSubClass, psBaseClass) {
function inheritance() {}
psSubClass.prototype = new inheritance();
psSubClass.prototype.constructor = psSubClass;
}
})
.endType();
