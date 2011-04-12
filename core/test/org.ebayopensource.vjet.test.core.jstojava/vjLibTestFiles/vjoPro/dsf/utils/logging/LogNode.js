vjo.ctype("vjoPro.dsf.utils.logging.LogNode")
.needs("vjoPro.dsf.utils.logging.LogManager")
.protos({
children:	null,
logger:		null,
parent:		null,

constructs: function(parent) {
this.parent = parent;
},

// Recursive method to walk the tree below a node and set
// a new parent logger.
walkAndSetParent: function(parent) {
var t = this;
if (t.children === null) {
return;
}
for(var i=0; i<t.children.length; i++){
var node = t.children[i];
if (node.logger === null) {
node.walkAndSetParent(parent);
} else {
this.vj$.LogManager.getLogManager().doSetParent(node.logger, parent);
}
}
}
})
.endType();
