vjo.ctype('access.scope.defaultModifier.child.ChildDefaultUser1')
.inherits('access.scope.defaultModifier.DefaultPerson1')
.protos({
	//> public void fillData()
	fillData :function(){
		var dPerson1 = new this.vj$.DefaultPerson1(); //<DefaultPerson1
		 vjo.sysout.println(dPerson1.m_defaultDept);
		 vjo.sysout.println(dPerson1.m_publicSex);
		 dPerson1.setName("HA");
	}
})
.endType();