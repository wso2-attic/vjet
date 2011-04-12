vjo.mtype('vjoPro.samples.otype.MShape')
//snippet.otype.begin
.needs('vjoPro.samples.otype.Shape')
//snippet.otype.end
.props({
//snippet.otype.begin
//>public Shape.func1 isOrigin(Shape.Point)
//snippet.otype.end
isOrigin: function(point) {
return point.x === 0 && point.y === 0;
},

//snippet.otype.begin
//> public Shape.func2 createLine(Object, Object)
//snippet.otype.end
createLine : function(pt1, pt2) {
this.line1 = {start: {x:pt1.x, y:pt1.y}, end: {x:pt2.x, y:pt2.y}};
}
})
.endType();
