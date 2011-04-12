package vjo.java.util;

import org.ebayopensource.dsf.aggregator.jsref.JsObj;
import org.ebayopensource.dsf.aggregator.jsref.internals.JsCmpMeta;
import org.ebayopensource.dsf.aggregator.jsref.JsObjData;
import org.ebayopensource.dsf.spec.component.IComponentSpec;
import org.ebayopensource.dsf.resource.pattern.js.JsResource;
import org.ebayopensource.dsf.resource.pattern.js.IJsResourceRef;
import vjo.java.lang.IllegalArgumentExceptionJsr;
import vjo.java.lang.IllegalStateExceptionJsr;
import vjo.java.lang.ComparableJsr;
import vjo.java.util.NoSuchElementExceptionJsr;
import vjo.java.util.AbstractSetJsr;
import vjo.java.util.MapJsr;
import vjo.java.lang.ObjectUtilJsr;
import vjo.java.lang.BooleanJsr;
import vjo.java.lang.NullPointerExceptionJsr;
import vjo.java.lang.CloneableJsr;
import vjo.java.util.AbstractMapJsr;

@org.ebayopensource.dsf.resource.utils.CodeGen("JsrGenerator")
public class TreeMapJsr<K,V> extends AbstractMapJsr<K,V> implements vjo.java.util.SortedMapJsr<K,V>, CloneableJsr {
    private static final long serialVersionUID = 1L;

    private static final JsObjData S = 
        new JsObjData("vjo.java.util.TreeMap", TreeMapJsr.class, "TreeMap");

    
    public static class ResourceSpec {
        public static IComponentSpec getInstance() {
            return S.getResourceSpec(); 
        }
        public static final JsResource RESOURCE = S.getJsResource();
        public static final IJsResourceRef REF = S.getJsResourceRef();
    }

    public static final IComponentSpec SPEC = S.getResourceSpec()
        .addDependentComponent(IllegalArgumentExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(IllegalStateExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(ComparableJsr.ResourceSpec.getInstance())
        .addDependentComponent(NoSuchElementExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(AbstractSetJsr.ResourceSpec.getInstance())
        .addDependentComponent(MapJsr.ResourceSpec.getInstance())
        .addDependentComponent(ObjectUtilJsr.ResourceSpec.getInstance())
        .addDependentComponent(BooleanJsr.ResourceSpec.getInstance())
        .addDependentComponent(NullPointerExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.ComparatorJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.SetJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.IteratorJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.CollectionJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.AbstractCollectionJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.ConcurrentModificationExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.SortedSetJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.SortedMapJsr.ResourceSpec.getInstance())
        .addDependentComponent(CloneableJsr.ResourceSpec.getInstance())
        .addDependentComponent(AbstractMapJsr.ResourceSpec.getInstance());

    protected TreeMapJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
        super(cmpMeta, isInstance, args);
    }

    public static abstract class PrivateEntryIteratorJsr<T,K,V> extends JsObj implements IteratorJsr<T> {
        private static final long serialVersionUID = 1L;

        private static final JsObjData S = 
            new JsObjData("vjo.java.util.TreeMap.PrivateEntryIterator", PrivateEntryIteratorJsr.class, "TreeMap");

        protected PrivateEntryIteratorJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
            super(cmpMeta, isInstance, args);
        }
    }
    public static class EntryJsr<K,V> extends JsObj implements vjo.java.util.MapJsr.EntryJsr<K,V> {
        private static final long serialVersionUID = 1L;

        private static final JsObjData S = 
            new JsObjData("vjo.java.util.TreeMap.Entry", EntryJsr.class, "TreeMap");

        protected EntryJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
            super(cmpMeta, isInstance, args);
        }
    }
}