vjo.ctype('access.scope.privateModifier.child.ChildPrivateUser1')
.inherits('access.scope.privateModifier.PrivatePerson1')
.protos({
	
	dPerson1 : null, //< private PrivatePerson1

	// void fillData()
	fillData :function(){
		this.dPerson1 = new this.vj$.PrivatePerson1();
		 vjo.sysout.println(this.dPerson1.m_defaultDept);
		 vjo.sysout.println(this.dPerson1.m_publicSex);
		 this.dPerson1.setName("HA");
	}
})
.endType();