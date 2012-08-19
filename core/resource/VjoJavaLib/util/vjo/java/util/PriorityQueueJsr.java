package vjo.java.util;

import org.ebayopensource.dsf.aggregator.jsref.internals.JsCmpMeta;
import org.ebayopensource.dsf.aggregator.jsref.JsObjData;
import org.ebayopensource.dsf.spec.component.IComponentSpec;
import org.ebayopensource.dsf.resource.pattern.js.JsResource;
import org.ebayopensource.dsf.resource.pattern.js.IJsResourceRef;
import vjo.java.lang.IllegalArgumentExceptionJsr;
import vjo.java.lang.SystemJsr;
import vjo.java.lang.NullPointerExceptionJsr;
import vjo.java.lang.IntegerJsr;
import vjo.java.util.QueueJsr;
import vjo.java.lang.ComparableJsr;
import vjo.java.util.NoSuchElementExceptionJsr;
import vjo.java.lang.IllegalStateExceptionJsr;
import vjo.java.lang.UtilJsr;
import vjo.java.util.AbstractQueueJsr;

@org.ebayopensource.dsf.resource.utils.CodeGen("JsrGenerator")
public class PriorityQueueJsr<E> extends AbstractQueueJsr<E> {
    private static final long serialVersionUID = 1L;

    private static final JsObjData S = 
        new JsObjData("vjo.java.util.PriorityQueue", PriorityQueueJsr.class, "PriorityQueue");

    
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
        .addDependentComponent(NullPointerExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(IntegerJsr.ResourceSpec.getInstance())
        .addDependentComponent(QueueJsr.ResourceSpec.getInstance())
        .addDependentComponent(ComparableJsr.ResourceSpec.getInstance())
        .addDependentComponent(NoSuchElementExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(IllegalStateExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(UtilJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.ComparatorJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.CollectionJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.SortedSetJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.IteratorJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.ArrayListJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.ConcurrentModificationExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.lang.MathJsr.ResourceSpec.getInstance())
        .addDependentComponent(AbstractQueueJsr.ResourceSpec.getInstance());

    protected PriorityQueueJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
        super(cmpMeta, isInstance, args);
    }

}