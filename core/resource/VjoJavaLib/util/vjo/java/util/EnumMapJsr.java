package vjo.java.util;

import org.ebayopensource.dsf.aggregator.jsref.JsObj;
import org.ebayopensource.dsf.aggregator.jsref.internals.JsCmpMeta;
import org.ebayopensource.dsf.aggregator.jsref.JsObjData;
import org.ebayopensource.dsf.spec.component.IComponentSpec;
import org.ebayopensource.dsf.resource.pattern.js.JsResource;
import org.ebayopensource.dsf.resource.pattern.js.IJsResourceRef;
import vjo.java.lang.IllegalArgumentExceptionJsr;
import vjo.java.lang.ClassCastExceptionJsr;
import vjo.java.lang.IllegalStateExceptionJsr;
import vjo.java.util.MapJsr;
import vjo.java.util.SetJsr;
import vjo.java.util.AbstractSetJsr;
import vjo.java.util.IteratorJsr;
import vjo.java.util.CollectionJsr;
import vjo.java.util.AbstractCollectionJsr;
import vjo.java.util.NoSuchElementExceptionJsr;
import vjo.java.lang.ClassUtilJsr;
import vjo.java.lang.ObjectUtilJsr;
import vjo.java.lang.CloneableJsr;
import vjo.java.util.AbstractMapJsr;
import org.ebayopensource.dsf.aggregator.jsref.JsEnum;

@org.ebayopensource.dsf.resource.utils.CodeGen("JsrGenerator")
public class EnumMapJsr<K extends JsEnum,V> extends AbstractMapJsr<K,V> implements CloneableJsr {
    private static final long serialVersionUID = 1L;

    private static final JsObjData S = 
        new JsObjData("vjo.java.util.EnumMap", EnumMapJsr.class, "EnumMap");

    
    public static class ResourceSpec {
        public static IComponentSpec getInstance() {
            return S.getResourceSpec(); 
        }
        public static final JsResource RESOURCE = S.getJsResource();
        public static final IJsResourceRef REF = S.getJsResourceRef();
    }

    public static final IComponentSpec SPEC = S.getResourceSpec()
        .addDependentComponent(IllegalArgumentExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(ClassCastExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(IllegalStateExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(MapJsr.ResourceSpec.getInstance())
        .addDependentComponent(SetJsr.ResourceSpec.getInstance())
        .addDependentComponent(AbstractSetJsr.ResourceSpec.getInstance())
        .addDependentComponent(IteratorJsr.ResourceSpec.getInstance())
        .addDependentComponent(CollectionJsr.ResourceSpec.getInstance())
        .addDependentComponent(AbstractCollectionJsr.ResourceSpec.getInstance())
        .addDependentComponent(NoSuchElementExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(ClassUtilJsr.ResourceSpec.getInstance())
        .addDependentComponent(ObjectUtilJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.ArraysJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.lang.reflect.ArrayJsr.ResourceSpec.getInstance())
        .addDependentComponent(CloneableJsr.ResourceSpec.getInstance())
        .addDependentComponent(AbstractMapJsr.ResourceSpec.getInstance());

    protected EnumMapJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
        super(cmpMeta, isInstance, args);
    }

    public static abstract class EnumMapIteratorJsr<K extends JsEnum,V,T> extends JsObj implements IteratorJsr<T> {
        private static final long serialVersionUID = 1L;

        private static final JsObjData S = 
            new JsObjData("vjo.java.util.EnumMap.EnumMapIterator", EnumMapIteratorJsr.class, "EnumMap");

        protected EnumMapIteratorJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
            super(cmpMeta, isInstance, args);
        }
    }
}