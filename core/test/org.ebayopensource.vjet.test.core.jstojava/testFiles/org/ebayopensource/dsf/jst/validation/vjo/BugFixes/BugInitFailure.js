vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.BugInitFailure")
.protos({
	validProp: 1,
	invalidProp: this.validProp,
	invalidProp2: this.vj$.BugInitFailure.validProp1
})
.props({
	validProp1: 2,
	invalidProp3: new this.vj$.BugInitFailure(),
	validProp2: new Date(),
	validProp3: new Array(1),
	validProp4: new Date().getTime(),
	validProp5: document.createElement('span')
})
.endType();