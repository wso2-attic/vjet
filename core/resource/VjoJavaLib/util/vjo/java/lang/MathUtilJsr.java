package vjo.java.lang;

import org.ebayopensource.dsf.aggregator.jsref.JsObj;
import org.ebayopensource.dsf.aggregator.jsref.internals.JsCmpMeta;
import org.ebayopensource.dsf.aggregator.jsref.JsObjData;
import org.ebayopensource.dsf.spec.component.IComponentSpec;
import org.ebayopensource.dsf.resource.pattern.js.JsResource;
import org.ebayopensource.dsf.resource.pattern.js.IJsResourceRef;
import vjo.java.lang.ShortJsr;
import vjo.java.lang.IntegerJsr;

@org.ebayopensource.dsf.resource.utils.CodeGen("JsrGenerator")
public class MathUtilJsr extends JsObj {
    private static final long serialVersionUID = 1L;

    private static final JsObjData S = 
        new JsObjData("vjo.java.lang.MathUtil", MathUtilJsr.class, "MathUtil");

    
    public static class ResourceSpec {
        public static IComponentSpec getInstance() {
            return S.getResourceSpec(); 
        }
        public static final JsResource RESOURCE = S.getJsResource();
        public static final IJsResourceRef REF = S.getJsResourceRef();
    }

    public static final IComponentSpec SPEC = S.getResourceSpec()
        .addDependentComponent(ShortJsr.ResourceSpec.getInstance())
        .addDependentComponent(IntegerJsr.ResourceSpec.getInstance());

    protected MathUtilJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
        super(cmpMeta, isInstance, args);
    }
}