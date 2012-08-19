package vjo.java.util;

import org.ebayopensource.dsf.aggregator.jsref.internals.JsCmpMeta;
import org.ebayopensource.dsf.aggregator.jsref.JsObjData;
import org.ebayopensource.dsf.spec.component.IComponentSpec;
import org.ebayopensource.dsf.resource.pattern.js.JsResource;
import org.ebayopensource.dsf.resource.pattern.js.IJsResourceRef;
import vjo.java.lang.MathJsr;
import vjo.java.util.LinkedHashMapJsr;
import vjo.java.lang.UtilJsr;
import vjo.java.util.AbstractCollectionJsr;
import vjo.java.util.SetJsr;
import vjo.java.lang.CloneableJsr;
import vjo.java.util.AbstractSetJsr;

@org.ebayopensource.dsf.resource.utils.CodeGen("JsrGenerator")
public class HashSetJsr<E> extends AbstractSetJsr<E> implements SetJsr<E>, CloneableJsr {
    private static final long serialVersionUID = 1L;

    private static final JsObjData S = 
        new JsObjData("vjo.java.util.HashSet", HashSetJsr.class, "HashSet");

    
    public static class ResourceSpec {
        public static IComponentSpec getInstance() {
            return S.getResourceSpec(); 
        }
        public static final JsResource RESOURCE = S.getJsResource();
        public static final IJsResourceRef REF = S.getJsResourceRef();
    }

    public static final IComponentSpec SPEC = S.getResourceSpec()
        .addDependentComponent(MathJsr.ResourceSpec.getInstance())
        .addDependentComponent(LinkedHashMapJsr.ResourceSpec.getInstance())
        .addDependentComponent(UtilJsr.ResourceSpec.getInstance())
        .addDependentComponent(AbstractCollectionJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.HashMapJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.CollectionJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.IteratorJsr.ResourceSpec.getInstance())
        .addDependentComponent(SetJsr.ResourceSpec.getInstance())
        .addDependentComponent(CloneableJsr.ResourceSpec.getInstance())
        .addDependentComponent(AbstractSetJsr.ResourceSpec.getInstance());

    protected HashSetJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
        super(cmpMeta, isInstance, args);
    }
}