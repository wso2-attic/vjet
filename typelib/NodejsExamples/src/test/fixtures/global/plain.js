foo = 'foo';
global.bar = 'bar';
//>@SUPPRESSTYPECHECK ; bar is exported as global
exports.fooBar = {foo: global.foo, bar: bar};
