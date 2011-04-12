vjo.ctype('vjoPro.samples.mtype.Employee1')
//snippet.mixin.begin
.mixin('vjoPro.samples.mtype.Person1')
//snippet.mixin.end
.props({
//> public void main(String[] args)
main : function(args)
{
//snippet.mixin.begin
this.doIt1();
var emp = new this.vj$.Employee1();
emp.doIt2();
//snippet.mixin.end
}
})
.endType();
