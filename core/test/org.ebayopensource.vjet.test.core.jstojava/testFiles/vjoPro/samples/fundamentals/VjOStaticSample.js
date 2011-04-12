vjo.ctype('vjoPro.samples.fundamentals.VjOStaticSample')
//snippet.props.begin
.props({
minBalance : 5.00,	//< private final double

//> public boolean meetsMinBalance(double)
meetsMinBalance: function(balance) {
return balance >= vjoPro.samples.fundamentals.VjOStaticSample.minBalance;
}
})
//snippet.props.end
.endType();
