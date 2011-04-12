vjo.otype('astjst.OTest') //< public
.defs({
	Point: {
		x: undefined, //< int
		y: undefined //< int
	},
	
	Line: {
		start: undefined, //< Point
		end:   undefined //< Point
	},

	//> public boolean isOrigin(Point pt)
	isOrigin: vjo.NEEDS_IMPL, 
	
	//> public void createLine(Point pt1, Point pt2)
	createLine: vjo.NEEDS_IMPL

})
.endType();