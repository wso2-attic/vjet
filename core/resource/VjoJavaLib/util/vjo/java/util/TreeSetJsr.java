package vjo.java.util;

import org.ebayopensource.dsf.aggregator.jsref.internals.JsCmpMeta;
import org.ebayopensource.dsf.aggregator.jsref.JsObjData;
import org.ebayopensource.dsf.spec.component.IComponentSpec;
import org.ebayopensource.dsf.resource.pattern.js.JsResource;
import org.ebayopensource.dsf.resource.pattern.js.IJsResourceRef;
import vjo.java.util.SetJsr;
import vjo.java.util.CollectionJsr;
import vjo.java.util.IteratorJsr;
import vjo.java.util.AbstractCollectionJsr;
import vjo.java.util.SortedSetJsr;
import vjo.java.lang.CloneableJsr;
import vjo.java.util.AbstractSetJsr;

@org.ebayopensource.dsf.resource.utils.CodeGen("JsrGenerator")
public class TreeSetJsr<E> extends AbstractSetJsr<E> implements SortedSetJsr<E>, CloneableJsr {
    private static final long serialVersionUID = 1L;

    private static final JsObjData S = 
        new JsObjData("vjo.java.util.TreeSet", TreeSetJsr.class, "TreeSet");

    
    public static class ResourceSpec {
        public static IComponentSpec getInstance() {
            return S.getResourceSpec(); 
        }
        public static final JsResource RESOURCE = S.getJsResource();
        public static final IJsResourceRef REF = S.getJsResourceRef();
    }

    public static final IComponentSpec SPEC = S.getResourceSpec()
        .addDependentComponent(SetJsr.ResourceSpec.getInstance())
        .addDependentComponent(CollectionJsr.ResourceSpec.getInstance())
        .addDependentComponent(IteratorJsr.ResourceSpec.getInstance())
        .addDependentComponent(AbstractCollectionJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.lang.NullPointerExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.SortedMapJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.TreeMapJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.ComparatorJsr.ResourceSpec.getInstance())
        .addDependentComponent(SortedSetJsr.ResourceSpec.getInstance())
        .addDependentComponent(CloneableJsr.ResourceSpec.getInstance())
        .addDependentComponent(AbstractSetJsr.ResourceSpec.getInstance());

    protected TreeSetJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
        super(cmpMeta, isInstance, args);
    }
}