package vjo.java.lang;

import org.ebayopensource.dsf.aggregator.jsref.internals.JsCmpMeta;
import org.ebayopensource.dsf.aggregator.jsref.JsObjData;
import org.ebayopensource.dsf.spec.component.IComponentSpec;
import org.ebayopensource.dsf.resource.pattern.js.JsResource;
import org.ebayopensource.dsf.resource.pattern.js.IJsResourceRef;
import vjo.java.lang.ThrowableJsr;
import vjo.java.lang.RuntimeExceptionJsr;

@org.ebayopensource.dsf.resource.utils.CodeGen("JsrGenerator")
public class IllegalArgumentExceptionJsr extends RuntimeExceptionJsr {
    private static final long serialVersionUID = 1L;

    private static final JsObjData S = 
        new JsObjData("vjo.java.lang.IllegalArgumentException", IllegalArgumentExceptionJsr.class, "IllegalArgumentException");

    
    public static class ResourceSpec {
        public static IComponentSpec getInstance() {
            return S.getResourceSpec(); 
        }
        public static final JsResource RESOURCE = S.getJsResource();
        public static final IJsResourceRef REF = S.getJsResourceRef();
    }

    public static final IComponentSpec SPEC = S.getResourceSpec()
        .addDependentComponent(ThrowableJsr.ResourceSpec.getInstance())
        .addDependentComponent(RuntimeExceptionJsr.ResourceSpec.getInstance());

    protected IllegalArgumentExceptionJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
        super(cmpMeta, isInstance, args);
    }
}