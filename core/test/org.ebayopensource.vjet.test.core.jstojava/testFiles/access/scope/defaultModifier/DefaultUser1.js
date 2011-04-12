vjo.ctype('access.scope.defaultModifier.DefaultUser1')
.needs('access.scope.defaultModifier.DefaultPerson1')
.protos({
	// void fillData()
	fillData :function(){
		var dPerson1 = new this.vj$.DefaultPerson1();//<DefaultPerson1
		 vjo.sysout.println(dPerson1.m_defaultDept);
		 vjo.sysout.println(dPerson1.m_publicSex);
		 dPerson1.setName("HA");
	}
})
.endType();