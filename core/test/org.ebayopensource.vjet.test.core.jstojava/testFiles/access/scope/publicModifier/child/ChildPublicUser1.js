vjo.ctype('access.scope.publicModifier.child.ChildPublicUser1')
.inherits('access.scope.publicModifier.PublicPerson1')
.protos({
		// void fillData()
	fillData :function(){
		var dPerson1 = new this.vj$.PublicPerson1();//<PublicPerson1
		 vjo.sysout.println(dPerson1.m_defaultDept);
		 vjo.sysout.println(dPerson1.m_publicSex);
		 dPerson1.setName("HA");
	}
})
.endType();