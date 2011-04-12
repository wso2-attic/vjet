package vjo.java.util;

import org.ebayopensource.dsf.aggregator.jsref.JsObj;
import org.ebayopensource.dsf.aggregator.jsref.internals.JsCmpMeta;
import org.ebayopensource.dsf.aggregator.jsref.JsObjData;
import org.ebayopensource.dsf.spec.component.IComponentSpec;
import org.ebayopensource.dsf.resource.pattern.js.JsResource;
import org.ebayopensource.dsf.resource.pattern.js.IJsResourceRef;
import vjo.java.lang.IllegalArgumentExceptionJsr;
import vjo.java.lang.SystemJsr;
import vjo.java.lang.IllegalStateExceptionJsr;
import vjo.java.util.MapJsr;
import vjo.java.util.NoSuchElementExceptionJsr;
import vjo.java.util.AbstractSetJsr;
import vjo.java.lang.UtilJsr;
import vjo.java.lang.ObjectUtilJsr;
import vjo.java.lang.CloneableJsr;
import vjo.java.util.AbstractMapJsr;

@org.ebayopensource.dsf.resource.utils.CodeGen("JsrGenerator")
public class IdentityHashMapJsr<K,V> extends AbstractMapJsr<K,V> implements MapJsr<K,V>, CloneableJsr {
    private static final long serialVersionUID = 1L;

    private static final JsObjData S = 
        new JsObjData("vjo.java.util.IdentityHashMap", IdentityHashMapJsr.class, "IdentityHashMap");

    
    public static class ResourceSpec {
        public static IComponentSpec getInstance() {
            return S.getResourceSpec(); 
        }
        public static final JsResource RESOURCE = S.getJsResource();
        public static final IJsResourceRef REF = S.getJsResourceRef();
    }

    public static final IComponentSpec SPEC = S.getResourceSpec()
        .addDependentComponent(IllegalArgumentExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(SystemJsr.ResourceSpec.getInstance())
        .addDependentComponent(IllegalStateExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(MapJsr.ResourceSpec.getInstance())
        .addDependentComponent(NoSuchElementExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(AbstractSetJsr.ResourceSpec.getInstance())
        .addDependentComponent(UtilJsr.ResourceSpec.getInstance())
        .addDependentComponent(ObjectUtilJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.IteratorJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.ConcurrentModificationExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.SetJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.CollectionJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.AbstractCollectionJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.ListJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.ArrayListJsr.ResourceSpec.getInstance())
        .addDependentComponent(CloneableJsr.ResourceSpec.getInstance())
        .addDependentComponent(AbstractMapJsr.ResourceSpec.getInstance());

    protected IdentityHashMapJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
        super(cmpMeta, isInstance, args);
    }

    public static abstract class IdentityHashMapIteratorJsr<K,V,T> extends JsObj implements IteratorJsr<T> {
        private static final long serialVersionUID = 1L;

        private static final JsObjData S = 
            new JsObjData("vjo.java.util.IdentityHashMap.IdentityHashMapIterator", IdentityHashMapIteratorJsr.class, "IdentityHashMap");

        protected IdentityHashMapIteratorJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
            super(cmpMeta, isInstance, args);
        }
    }
}