vjo.ctype('bugs.b2192.Bug2192C')
.protos({
        age: 0, //<int
        name: null, //<String
        address: null, //<String
        //>public void constructs(int age,int name)
        constructs: function(name, age) {
                this.age = age;
                this.name= name;//<<String
        }
}).endType()