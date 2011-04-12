package vjo.java.util;

import org.ebayopensource.dsf.aggregator.jsref.JsObj;
import org.ebayopensource.dsf.aggregator.jsref.internals.JsCmpMeta;
import org.ebayopensource.dsf.aggregator.jsref.JsObjData;
import org.ebayopensource.dsf.spec.component.IComponentSpec;
import org.ebayopensource.dsf.resource.pattern.js.JsResource;
import org.ebayopensource.dsf.resource.pattern.js.IJsResourceRef;
import vjo.java.lang.UnsupportedOperationExceptionJsr;
import vjo.java.lang.NullPointerExceptionJsr;
import vjo.java.util.AbstractSetJsr;
import vjo.java.lang.ClassCastExceptionJsr;
import vjo.java.lang.ObjectUtilJsr;

@org.ebayopensource.dsf.resource.utils.CodeGen("JsrGenerator")
public abstract class AbstractMapJsr<K,V> extends JsObj implements vjo.java.util.MapJsr<K,V> {
    private static final long serialVersionUID = 1L;

    private static final JsObjData S = 
        new JsObjData("vjo.java.util.AbstractMap", AbstractMapJsr.class, "AbstractMap");

    
    public static class ResourceSpec {
        public static IComponentSpec getInstance() {
            return S.getResourceSpec(); 
        }
        public static final JsResource RESOURCE = S.getJsResource();
        public static final IJsResourceRef REF = S.getJsResourceRef();
    }

    public static final IComponentSpec SPEC = S.getResourceSpec()
        .addDependentComponent(UnsupportedOperationExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(NullPointerExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(AbstractSetJsr.ResourceSpec.getInstance())
        .addDependentComponent(ClassCastExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(ObjectUtilJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.IteratorJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.SetJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.CollectionJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.AbstractCollectionJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.MapJsr.ResourceSpec.getInstance());

    protected AbstractMapJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
        super(cmpMeta, isInstance, args);
    }

    public static class SimpleEntryJsr<K,V> extends JsObj implements EntryJsr<K,V> {
        private static final long serialVersionUID = 1L;

        private static final JsObjData S = 
            new JsObjData("vjo.java.util.AbstractMap.SimpleEntry", SimpleEntryJsr.class, "AbstractMap");

        protected SimpleEntryJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
            super(cmpMeta, isInstance, args);
        }
    }
}