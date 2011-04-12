vjo.ctype('defect.Bug4072') //<public
.needs('defect.Bug4072_Parent')
.props({
  sprop1 : 10,
  sprop2 : 12,
  sfun1 : function() {
		Bug4072_P
  }
})
.protos({
  prop1 : 12,
  prop2 : 12,
  fun1 : function() {
		Bug4072_P
  }
})
.endType();
