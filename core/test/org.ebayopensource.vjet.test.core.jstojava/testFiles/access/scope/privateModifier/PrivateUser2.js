vjo.ctype('access.scope.privateModifier.PrivateUser2')
.needs('access.scope.defaultModifier.DefaultPerson1')
.protos({
	
	dPerson1 : null, //< private DefaultPerson1

	// void fillData()
	fillData :function(){
		this.dPerson1 = new this.vj$.DefaultPerson1();
		 vjo.sysout.println(this.dPerson1.m_defaultDept);
		 vjo.sysout.println(this.dPerson1.m_publicSex);
		 this.dPerson1.setName("HA");
	}
})
.endType();