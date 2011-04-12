package vjo.java.lang;

import org.ebayopensource.dsf.aggregator.jsref.internals.JsCmpMeta;
import org.ebayopensource.dsf.aggregator.jsref.JsObjData;
import org.ebayopensource.dsf.spec.component.IComponentSpec;
import org.ebayopensource.dsf.resource.pattern.js.JsResource;
import org.ebayopensource.dsf.resource.pattern.js.IJsResourceRef;
import vjo.java.lang.IntegerJsr;
import vjo.java.lang.NumberFormatExceptionJsr;
import vjo.java.lang.UtilJsr;
import vjo.java.lang.NumberUtilJsr;
import vjo.java.lang.ComparableJsr;
import vjo.java.lang.NumberJsr;

@org.ebayopensource.dsf.resource.utils.CodeGen("JsrGenerator")
public final class ShortJsr extends NumberJsr implements ComparableJsr<vjo.java.lang.ShortJsr> {
    private static final long serialVersionUID = 1L;

    private static final JsObjData S = 
        new JsObjData("vjo.java.lang.Short", ShortJsr.class, "Short");

    
    public static class ResourceSpec {
        public static IComponentSpec getInstance() {
            return S.getResourceSpec(); 
        }
        public static final JsResource RESOURCE = S.getJsResource();
        public static final IJsResourceRef REF = S.getJsResourceRef();
    }

    public static final IComponentSpec SPEC = S.getResourceSpec()
        .addDependentComponent(IntegerJsr.ResourceSpec.getInstance())
        .addDependentComponent(NumberFormatExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(UtilJsr.ResourceSpec.getInstance())
        .addDependentComponent(NumberUtilJsr.ResourceSpec.getInstance())
        .addDependentComponent(ComparableJsr.ResourceSpec.getInstance())
        .addDependentComponent(NumberJsr.ResourceSpec.getInstance());

    protected ShortJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
        super(cmpMeta, isInstance, args);
    }

}