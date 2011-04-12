vjo.ctype('bugs.b2192.Bug2192B')
.protos({
        age: 0, //<int
        name: null, //<String
        address: null, //<String
        //>public void constructs(int age,String name)
        constructs: function(age, name) {
                this.age = age;
                this.name= name;
        }
}).endType()