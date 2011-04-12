package vjo.java.util;

import org.ebayopensource.dsf.aggregator.jsref.JsObj;
import org.ebayopensource.dsf.aggregator.jsref.internals.JsCmpMeta;
import org.ebayopensource.dsf.aggregator.jsref.JsObjData;
import org.ebayopensource.dsf.spec.component.IComponentSpec;
import org.ebayopensource.dsf.resource.pattern.js.JsResource;
import org.ebayopensource.dsf.resource.pattern.js.IJsResourceRef;
import vjo.java.lang.UnsupportedOperationExceptionJsr;
import vjo.java.lang.IndexOutOfBoundsExceptionJsr;
import vjo.java.util.NoSuchElementExceptionJsr;
import vjo.java.lang.IllegalStateExceptionJsr;
import vjo.java.util.RandomAccessJsr;
import vjo.java.util.RandomAccessSubListJsr;
import vjo.java.lang.NullPointerExceptionJsr;
import vjo.java.util.SubListJsr;
import vjo.java.lang.ObjectUtilJsr;
import vjo.java.util.ListJsr;
import vjo.java.util.AbstractCollectionJsr;

@org.ebayopensource.dsf.resource.utils.CodeGen("JsrGenerator")
public abstract class AbstractListJsr<E> extends AbstractCollectionJsr<E> implements ListJsr<E> {
    private static final long serialVersionUID = 1L;

    private static final JsObjData S = 
        new JsObjData("vjo.java.util.AbstractList", AbstractListJsr.class, "AbstractList");

    
    public static class ResourceSpec {
        public static IComponentSpec getInstance() {
            return S.getResourceSpec(); 
        }
        public static final JsResource RESOURCE = S.getJsResource();
        public static final IJsResourceRef REF = S.getJsResourceRef();
    }

    public static final IComponentSpec SPEC = S.getResourceSpec()
        .addDependentComponent(UnsupportedOperationExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(IndexOutOfBoundsExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(NoSuchElementExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(IllegalStateExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(RandomAccessJsr.ResourceSpec.getInstance())
        .addDependentComponent(RandomAccessSubListJsr.ResourceSpec.getInstance())
        .addDependentComponent(NullPointerExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(SubListJsr.ResourceSpec.getInstance())
        .addDependentComponent(ObjectUtilJsr.ResourceSpec.getInstance())
        .addDependentComponent(ListJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.IteratorJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.CollectionJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.ConcurrentModificationExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(AbstractCollectionJsr.ResourceSpec.getInstance());

    protected AbstractListJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
        super(cmpMeta, isInstance, args);
    }

    public static class ItrJsr<E> extends JsObj implements IteratorJsr<E> {
        private static final long serialVersionUID = 1L;

        private static final JsObjData S = 
            new JsObjData("vjo.java.util.AbstractList.Itr", ItrJsr.class, "AbstractList");

        protected ItrJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
            super(cmpMeta, isInstance, args);
        }
    }
}