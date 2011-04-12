package vjo.java.util;

import org.ebayopensource.dsf.aggregator.jsref.JsObjData;
import org.ebayopensource.dsf.spec.component.IComponentSpec;
import org.ebayopensource.dsf.resource.pattern.js.JsResource;
import org.ebayopensource.dsf.resource.pattern.js.IJsResourceRef;
import vjo.java.util.IteratorJsr;

@org.ebayopensource.dsf.resource.utils.CodeGen("JsrGenerator")
public interface ListIteratorJsr<E> extends IteratorJsr<E> {
    JsObjData S = 
        new JsObjData("vjo.java.util.ListIterator", ListIteratorJsr.class, "ListIterator");

    
    public static class ResourceSpec {
        public static IComponentSpec getInstance() {
            return S.getResourceSpec(); 
        }
        public static final JsResource RESOURCE = S.getJsResource();
        public static final IJsResourceRef REF = S.getJsResourceRef();
    }

    public static final IComponentSpec SPEC = S.getResourceSpec()
        .addDependentComponent(IteratorJsr.ResourceSpec.getInstance());
}