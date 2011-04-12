<<0>>vjo<<1>>.<<2>>ctype('<<3>>handler.Sample')

.<<4>>needs('<<5>>nonStaticPropAdvisor.ProtosAdvisorTest')

.<<6>>needs('<<7>>nonStaticPropAdvisor.ProtosAdvisorTest1', 'myAlias1')

.<<8>>inherits('<<9>>nonStaticPropAdvisor.ProtosAdvisorAType')

.<<10>>satisfies('<<11>>nonStaticPropAdvisor.ProtosAdvisorIType')

.<<12>>mixin('<<13>>nonStaticPropAdvisor.ProtosAdvisorMType')

.<<14>>props({
    <<15>>staticProperty1 : null,  //< public staticProperty1

    sta<<17>>ticProperty2 : null, //< private staticProperty2

    staticFunc1<<19>> : <<20>>function(<<21>>) { //< public void staticFunc1()

      <<22>>va<<23>>r  x  =  <<24>>Math<<25>>.<<26>>random(); 

              var y = new <<27>>Date()<<28>>.<<29>>getHours();

              win<<30>>dow.<<31>>alert("");

              doc<<32>>ument.<<33>>getElementById("");
    },

    staticFunc2<<34>> : function(<<35>>str) { //< private void staticFunc2(String str)

    },
<<36>>
    main : function() { //< public void main(String ... argguments)
      
    }

})
.<<37>>protos({
<<38>>
    property1 : 10, //< public property1

    propert<<40>>y2 : 10, //< private property2

    con<<42>>structs<<43>> : function(<<44>>) { //< public constructs()
        <<45>>
    },

    func1<<46>> : <<47>>function(<<48>>) { //< public void func1()

      <<49>>va<<50>>r  xx  =  <<51>>Math<<52>>.<<53>>random(); 

              var yy = new <<54>>Date()<<55>>.<<56>>getHours();

              var s = '<<76>>'; //<String

              win<<57>>dow.<<58>>alert("");

              doc<<59>>ument.<<60>>getElementById("");

              var v = <<61>>window.<<62>>alert("hello world");
              
              var v1 = this.<<63>>toString();

              var v2 = this.vj$.<<64>>myAlias1.<<65>>getClass();

              this.<<66>>toString();

              this.vj$.<<67>>myAlias1.<<68>>getClass();

              this.vj$.<<69>>ProtosAdvisorTest1.<<70>>getClass();
    },

    func2 : function() { //< private void func2()
    	var i = 100;
        var f;//<void function(int);
        f(<<77>>);
    }

})
.<<71>>inits (function(){
<<72>>
})
.<<73>>endType()<<74>>;<<75>>