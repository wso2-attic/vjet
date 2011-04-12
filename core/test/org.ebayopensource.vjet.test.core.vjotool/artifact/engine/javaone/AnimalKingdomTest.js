vjo.ctype('engine.javaone.AnimalKingdomTest') //< public
.needs(['engine.javaone.Lion',
        'engine.javaone.Tiger',
        'engine.javaone.Liger'])
.props({
	main:function() {
		var lion = new this.vj$.Lion('Leo', 450, true); //< Lion
		var tigress = new this.vj$.Tiger('Tigris', 350, false); //< Tiger
		var son = new this.vj$.Liger(lion, tigress, 'Hercules', true); //< Liger
		if (<<56>>son.<<57>>getFather() === lion) {
			<<58>>vjo.<<59>>sysout.<<60>>println("object identity is preserved");
		}
		vjo.sysout.println(son.<<61>>getGene());
		vjo.sysout.println(son.getWeight());
		vjo.sysout.println(son.getFatherName());
		vjo.sysout.println(son.getMotherName());
		
		var hasMarriedParents = son.<<62>>areParentsMarried();
		vjo.sysout.println('marriedParents: ' + <<63>>hasMarriedParents);
		lion.marryTo(tigress);
		vjo.sysout.println('marriedParents: ' + son.<<64>>areParentsMarried());
	}
})
.endType();