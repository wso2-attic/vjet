vjo.ctype('bugs.b3238.Bug3238')
.protos({
        age: 0, //<int
        name: null, //<String
        address: null, //<String
        //>public void constructs(int name, int age, String address)
        constructs: function(name, age, address) {
                this.age = age;
                this.name= name;
                this.address = address;
		}
}).endType()