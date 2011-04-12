vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5720")
.props({
		
	BigO:function (data)
	{
	var order = 0;
	var origLength = data.X.length;
	/*
	while (data.X.length > 2){
		var lr = new LinearRegression(data);
		if (lr.b > 1e-6){
		// only increase the order if the slope
		// is "great" enough
		order++;
		}
	
		if (lr.r > 0.98 || lr.Syx < 1 || lr.b < 1e-6){
		// terminate if close to a line lr.r
		// small error lr.Syx
		// small slope lr.b
		break;
		}
		data = dataDeriv(data);
	}
	
	if (2 == origLength - order)
	{
	order = Number.POSITIVE_INFINITY;
	}
	return order;
	*/
	function LinearRegression(data)
	{
	/*
	y = a + bx
	for data points (Xi, Yi); 0 <= i < n
	
	b = (n*SUM(XiYi) - SUM(Xi)*SUM(Yi))/(n*SUM(Xi*Xi) - SUM(Xi)*SUM(Xi))
	a = (SUM(Yi) - b*SUM(Xi))/n
	*/
	var i;
	
	if (data.X.length != data.Y.length)
	{
	throw 'LinearRegression: data point length mismatch';
	}
	if (data.X.length < 3)
	{
	throw 'LinearRegression: data point length < 2';
	}
	var n = data.X.length;
	var X = data.X;
	var Y = data.Y;
	
	this.Xavg = 0;
	this.Yavg = 0;
	
	var SUM_X  = 0;
	var SUM_XY = 0;
	var SUM_XX = 0;
	var SUM_Y  = 0;
	var SUM_YY = 0;
	
	for (i = 0; i < n; i++)
	{
	SUM_X  += X[i];
	SUM_XY += X[i]*Y[i];
	SUM_XX += X[i]*X[i];
	SUM_Y  += Y[i];
	SUM_YY += Y[i]*Y[i];
	}
	
	this.b = (n * SUM_XY - SUM_X * SUM_Y)/(n * SUM_XX - SUM_X * SUM_X);
	this.a = (SUM_Y - this.b * SUM_X)/n;
	
	this.Xavg = SUM_X/n;
	this.Yavg = SUM_Y/n;
	
	var SUM_Ydiff2 = 0;
	var SUM_Xdiff2 = 0;
	var SUM_XdiffYdiff = 0;
	
	for (i = 0; i < n; i++)
	{
	var Ydiff = Y[i] - this.Yavg;
	var Xdiff = X[i] - this.Xavg;
	
	SUM_Ydiff2 += Ydiff * Ydiff;
	SUM_Xdiff2 += Xdiff * Xdiff;
	SUM_XdiffYdiff += Xdiff * Ydiff;
	}
	
	var Syx2 = (SUM_Ydiff2 - Math.pow(SUM_XdiffYdiff/SUM_Xdiff2, 2))/(n - 2);
	var r2   = Math.pow((n*SUM_XY - SUM_X * SUM_Y), 2) /
	((n*SUM_XX - SUM_X*SUM_X)*(n*SUM_YY-SUM_Y*SUM_Y));
	
	this.Syx = Math.sqrt(Syx2);
	this.r = Math.sqrt(r2);
	
	}
	
	function dataDeriv(data)
	{
	if (data.X.length != data.Y.length)
	{
	throw 'length mismatch';
	}
	var length = data.X.length;
	
	if (length < 2)
	{
	throw 'length ' + length + ' must be >= 2';
	}
	var X = data.X;
	var Y = data.Y;
	
	var deriv = {X: [], Y: [] };
	
	for (var i = 0; i < length - 1; i++)
	{
	deriv.X[i] = (X[i] + X[i+1])/2;
	deriv.Y[i] = (Y[i+1] - Y[i])/(X[i+1] - X[i]);
	}
	return deriv;
	}
	
	return 0;
	}
})
.endType();