package vjo.java.lang;

import org.ebayopensource.dsf.aggregator.jsref.JsObjData;
import org.ebayopensource.dsf.spec.component.IComponentSpec;
import org.ebayopensource.dsf.resource.pattern.js.JsResource;
import org.ebayopensource.dsf.resource.pattern.js.IJsResourceRef;

@org.ebayopensource.dsf.resource.utils.CodeGen("JsrGenerator")
public interface CharSequenceJsr {
    JsObjData S = 
        new JsObjData("vjo.java.lang.CharSequence", CharSequenceJsr.class, "CharSequence");

    
    public static class ResourceSpec {
        public static IComponentSpec getInstance() {
            return S.getResourceSpec(); 
        }
        public static final JsResource RESOURCE = S.getJsResource();
        public static final IJsResourceRef REF = S.getJsResourceRef();
    }

    public static final IComponentSpec SPEC = S.getResourceSpec();
}