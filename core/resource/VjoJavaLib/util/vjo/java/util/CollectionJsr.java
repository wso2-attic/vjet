package vjo.java.util;

import org.ebayopensource.dsf.aggregator.jsref.JsObjData;
import org.ebayopensource.dsf.spec.component.IComponentSpec;
import org.ebayopensource.dsf.resource.pattern.js.JsResource;
import org.ebayopensource.dsf.resource.pattern.js.IJsResourceRef;
import vjo.java.lang.IterableJsr;

@org.ebayopensource.dsf.resource.utils.CodeGen("JsrGenerator")
public interface CollectionJsr<E> extends IterableJsr<E> {
    JsObjData S = 
        new JsObjData("vjo.java.util.Collection", CollectionJsr.class, "Collection");

    
    public static class ResourceSpec {
        public static IComponentSpec getInstance() {
            return S.getResourceSpec(); 
        }
        public static final JsResource RESOURCE = S.getJsResource();
        public static final IJsResourceRef REF = S.getJsResourceRef();
    }

    public static final IComponentSpec SPEC = S.getResourceSpec()
        .addDependentComponent(vjo.java.util.IteratorJsr.ResourceSpec.getInstance())
        .addDependentComponent(IterableJsr.ResourceSpec.getInstance());
}