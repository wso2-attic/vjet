vjo.ctype("vjoPro.dsf.utils.jstrace.Trace")
.needs("vjoPro.dsf.utils.jstrace.Wrap")
.needs("vjoPro.dsf.utils.jstrace.TraceLogger")
.protos({
W : vjoPro.dsf.utils.jstrace.Wrap,//<Type::Wrap
L : vjoPro.dsf.utils.jstrace.TraceLogger,//<Type::TraceLogger
logger:null,//<TraceLogger
/**
* @access public
* @param {String} exp
* @param {java.util.Map} cfg
*/
//> public constructs(String exp, Object cfg)
constructs : function( exp, cfg){
var t = this;//<Trace

t.logger = new t.L(cfg);

//logger should go before wrap
t.W.wrap(exp, cfg);
}
})
.endType();
