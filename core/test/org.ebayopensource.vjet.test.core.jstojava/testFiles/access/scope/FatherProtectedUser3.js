vjo.ctype('access.scope.FatherProtectedUser3')
.needs('access.scope.protectedModifier.ProtectedPerson1')
.protos({
	// void fillData()
	fillData :function(){
		var dPerson1 = new this.vj$.ProtectedPerson1();//<ProtectedPerson1
		 vjo.sysout.println(dPerson1.m_defaultDept);
		 vjo.sysout.println(dPerson1.m_publicSex);
		 dPerson1.setName("HA");
	}
})
.endType();