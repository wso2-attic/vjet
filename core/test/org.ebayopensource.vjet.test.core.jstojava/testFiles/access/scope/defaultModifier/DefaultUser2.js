vjo.ctype('access.scope.defaultModifier.DefaultUser2')
.needs('access.scope.privateModifier.PrivatePerson1')
.protos({
	// void fillData()
	fillData :function(){
		var dPerson1 = new this.vj$.PrivatePerson1();//<PrivatePerson1
		 vjo.sysout.println(dPerson1.m_defaultDept);
		 vjo.sysout.println(dPerson1.m_publicSex);
		 dPerson1.setName("HA");
	}
})
.endType();