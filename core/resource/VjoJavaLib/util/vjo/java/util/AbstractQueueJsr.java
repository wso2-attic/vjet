package vjo.java.util;

import org.ebayopensource.dsf.aggregator.jsref.internals.JsCmpMeta;
import org.ebayopensource.dsf.aggregator.jsref.JsObjData;
import org.ebayopensource.dsf.spec.component.IComponentSpec;
import org.ebayopensource.dsf.resource.pattern.js.JsResource;
import org.ebayopensource.dsf.resource.pattern.js.IJsResourceRef;
import vjo.java.lang.NullPointerExceptionJsr;
import vjo.java.lang.IllegalArgumentExceptionJsr;
import vjo.java.lang.IllegalStateExceptionJsr;
import vjo.java.util.NoSuchElementExceptionJsr;
import vjo.java.util.QueueJsr;
import vjo.java.util.AbstractCollectionJsr;

@org.ebayopensource.dsf.resource.utils.CodeGen("JsrGenerator")
public abstract class AbstractQueueJsr<E> extends AbstractCollectionJsr<E> implements QueueJsr<E> {
    private static final long serialVersionUID = 1L;

    private static final JsObjData S = 
        new JsObjData("vjo.java.util.AbstractQueue", AbstractQueueJsr.class, "AbstractQueue");

    
    public static class ResourceSpec {
        public static IComponentSpec getInstance() {
            return S.getResourceSpec(); 
        }
        public static final JsResource RESOURCE = S.getJsResource();
        public static final IJsResourceRef REF = S.getJsResourceRef();
    }

    public static final IComponentSpec SPEC = S.getResourceSpec()
        .addDependentComponent(NullPointerExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(IllegalArgumentExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(IllegalStateExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(NoSuchElementExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.CollectionJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.IteratorJsr.ResourceSpec.getInstance())
        .addDependentComponent(QueueJsr.ResourceSpec.getInstance())
        .addDependentComponent(AbstractCollectionJsr.ResourceSpec.getInstance());

    protected AbstractQueueJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
        super(cmpMeta, isInstance, args);
    }
}