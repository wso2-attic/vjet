vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.rt.ftype.FTypeUser")
//>needs(require)
//>needs(org.ebayopensource.dsf.jst.validation.vjo.rt.ftype.FTypeGlobals)
//>needs(org.ebayopensource.dsf.jst.validation.vjo.rt.ftype.FType)
.globals({
	eReq: vjo.getType("require"),//<<require
	eF: vjo.getType("org.ebayopensource.dsf.jst.validation.vjo.rt.ftype.FType")//<<FType
}, gReq)
.props({
	main: function(){
		var f = null;//<FType
		var i = f(new Date());//<int
		i = f.refs;
		f.ref();
		
		var req = vjo.getType('require');//<<require
		req("net");
		var p = req.path;//<String[]
		p[0] = req.resolve();
		
		req.apply(this);
		req.call(this);
		alert(req.arguments[0]);
		
		var req1 = vjo.getType('require1');//<<require
		var req2 = vjo.getType('require');//<<require2
		
		//adding test for constructor error case
		var fObj = new f();//<<FType
		var reqObj = new req();//<<require
		
		gReq();
		gReq.apply(this);
		gReq.call(this);
		alert(gReq.arguments[0]);
		var gReqPathArr = gReq.path;//<String[]
		gReqPathArr[0] = gReq.resolve();
		
		gReq.eReq();
		gReq.eReq.apply(this);
		gReq.eReq.call(this);
		alert(gReq.eReq.arguments[0]);
		var eReqPathArr = gReq.eReq.path;//<String[]
		eReqPathArr[0] = gReq.eReq.resolve();
		
		//FType reference and type::FType testing
		gF();
		gF.apply(this);
		gF.call(this);
		alert(gF.arguments[0]);
		alert(gF.refs);
		gF.ref();
		
		gFType();
		gFType.apply(this);
		gFType.call(this);
		alert(gFType.arguments[0]);
		alert(gFType.refs);
		gFType.ref();
		
		gReq.eF();
		gReq.eF.apply(this);
		gReq.eF.call(this);
		alert(gReq.eF.arguments[0]);
		alert(gReq.eF.refs);
		gReq.eF.ref();
		
		//FTYPE assignment validation
		//expecting ftype assignable to some local function with same signature as
		//FTYPE's invoke
		var localFNotAssignable = function(){//<int function(int)
			return -1;
		}
		//all of the following assignments are wrong
		localFNotAssignable = f;
		localFNotAssignable = gF;
		localFNotAssignable = gReq.eF;
		
		var localFAssignable = function(){//<int function(Date)
			return 0;
		}
		//all of the following assignments should be allowed
		localFAssignable = f;
		localFAssignable = gF;
		localFAssignable = gReq.eF;
	}
})
.endType();