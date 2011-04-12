vjo.ctype('vjoPro.samples.foundations.CommentDeclarationSample')
.props({
//Members declared in props cascade are static

var1 : 'ghi', //< String

var2 : 'jkl', //<         String

//> private double
var3 : 7.89,

var4 : undefined, //< public int

//> public void static1(int i)
static1 : function(i)
{
alert('I am public static method static1');
alert('I accept int parameter and return nothing');
},

static2 : function(i) //< public double static2(String s)
{
alert('I am public static method static2');
alert('I accept String parameter and return double');
return this.var3;
},

static3 : function(i) //< private String static3(String s, int i)
{
alert('I am private static method static3');
alert('I accept String and int parameter and return String');
return this.var1;
}

})
.protos({

//Members declared protos cascade are instance (non-static)

//> public int
var5 : 123,

var6 : 456, //< public int

var7 : 'abc', //< protected String

var8 : 'def', //<         protected             String

var9 : true, //<     boolean

//> public void constructs(String s)
constructs: function(s)
{
alert('I am constructor of public VjO Class CommentDeclarationSample.');
alert('I accept String parameter and being a constructor, never return anything');
},

//> public void instance1()
instance1 : function()
{
alert('I am instance method instance1');
alert('I accept no parameter and return nothing');
},

instance2 : function(s, i) //< public boolean instance2(String s, int i)
{
alert('I am instance method instance2');
alert('I accept String and int parameter and return nothing');
return this.var9;
}

})
.inits(function () {
this.var4 = 100;
alert('I am static initializer of public VjO Class CommentDeclarationSample');
})
.endType();
