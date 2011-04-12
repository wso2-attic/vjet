vjo.ctype('vjoPro.samples.enums.eTypeForLoopEx')
.needs('vjoPro.samples.enums.eTypeDefStyle1')
.props({
//> public void main(String[] args)
main:function(args){
var days = this.vj$.eTypeDefStyle1.values();
document.writeln('Demonstrating for loop');
//snippet.forloop.begin
for(var i in days)
{
document.writeln('Day at ' + i + ' is ' + days[i].name()) ;
}
//snippet.forloop.end

document.writeln('Demonstrating for each loop');
//snippet.foreachloop.begin
for (var item in days) {
document.writeln('Day at ' + item.ordinal() + ' is ' + item.name()) ;
}
//snippet.foreachloop.end
}
})
.endType();
