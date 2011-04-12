vjo.ctype('vjoPro.samples.otype.ConstructShape')
.needs('vjoPro.samples.otype.Shape')
//> needs vjo.samples.otype.Shape
//snippet.otype.end
.props({
//snippet.otype.begin
point1 : null, //< Shape.Point
point2 : null, //< Shape.Point
line1 : undefined, //< Shape.Line
//snippet.otype.end

//snippet.otype.begin
//>public boolean isOrigin(Shape.Point)
//snippet.otype.end
isOrigin: function(point) {
return point.x === 0 && point.y === 0;
},

//snippet.otype.begin
//> public void createLine(Shape.Point, Shape.Point)
//snippet.otype.end
createLine : function(pt1, pt2) {
this.line1 = {start: {x:pt1.x, y:pt1.y}, end: {x:pt2.x, y:pt2.y}};
},

//> public void main(String[] args)
main : function(args) {
document.writeln('The Point pt1 is origin : ' + this.isOrigin(this.point1));

document.writeln('The Point pt2 is origin : ' + this.isOrigin(this.point2));

this.createLine(this.point1, this.point2);
}
})
.endType();
