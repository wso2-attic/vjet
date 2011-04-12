vjo.itype('vjoPro.samples.fundamentals.IEmployer')
//snippet.inheritance.begin
.inherits('vjoPro.samples.fundamentals.IBase')
//snippet.inheritance.end
.protos({
//> public int getEmpId()
getEmpId : function(){},

//> public String getName()
getName : function(){}
})
.props({
employername : 'eBay', //< public String
employerzipcode : 95125 //< public int
})
.endType();
