
function props(e, onePerLine, des)
{
 if (e === null) {
 // println("props called with null argument", "error");
 return des;
 }

 if (e === undefined) {
 // println("props called with undefined argument", "error");
 return des;
 }

 var ns = ["Methods", "Fields", "Unreachables"];
 var as = [[], [], []]; // array of (empty) arrays of arrays!
 var p, j, i; // loop variables, several used multiple times

 var protoLevels = 0;

 for (p = e; p; p = p.__proto__)
 {
 for (i=0; i<ns.length; ++i)
 as[i][protoLevels] = [];
 ++protoLevels;
 }

 for(var a in e)
 {
 // Shortcoming: doesn't check that VALUES are the same in object and
	// prototype.

 var protoLevel = -1;
 try
 {
 for (p = e; p && (a in p); p = p.__proto__)
 ++protoLevel;
 }
 catch(er) { protoLevel = 0; } // "in" operator throws when param to props() is
								// a string

 var type = 1;
 try
 {
 if ((typeof e[a]) == "function")
 type = 0;
 }
 catch (er) { type = 2; }

 as[type][protoLevel].push(a);
 }
	
 function times(s, n) { return n ? s + times(s, n-1) : ""; }
 var result = "";
 for (j=0, prefix = ""; j<protoLevels; ++j) {
	 var hasContent = false;
	 for (i=0; i<ns.length; ++i) {
		 if (as[i][j].length){
			 result = result + ns[i] + prefix + ": "  +
			 (onePerLine ? "\n" : "") + as[i][j].sort().join(onePerLine ? "\n" : ", ") + (onePerLine ? "\n" : "");
			 result = result + "\n"
			 hasContent = true;
		 }
	 }
	 prefix = prefix + " of prototype";
	 if (hasContent) {
		 result = result + "\n";
	 }		 
 }
 return result;
}
