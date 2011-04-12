package vjo.java.util;

import org.ebayopensource.dsf.aggregator.jsref.internals.JsCmpMeta;
import org.ebayopensource.dsf.aggregator.jsref.JsObjData;
import org.ebayopensource.dsf.spec.component.IComponentSpec;
import org.ebayopensource.dsf.resource.pattern.js.JsResource;
import org.ebayopensource.dsf.resource.pattern.js.IJsResourceRef;
import vjo.java.lang.IllegalArgumentExceptionJsr;
import vjo.java.lang.MathJsr;
import vjo.java.lang.SystemJsr;
import vjo.java.lang.reflect.ArrayJsr;
import vjo.java.lang.IndexOutOfBoundsExceptionJsr;
import vjo.java.lang.IntegerJsr;
import vjo.java.lang.UtilJsr;
import vjo.java.lang.ObjectUtilJsr;
import vjo.java.lang.ArrayIndexOutOfBoundsExceptionJsr;
import vjo.java.util.ListJsr;
import vjo.java.util.RandomAccessJsr;
import vjo.java.lang.CloneableJsr;
import vjo.java.util.AbstractListJsr;

@org.ebayopensource.dsf.resource.utils.CodeGen("JsrGenerator")
public class ArrayListJsr<E> extends AbstractListJsr<E> implements ListJsr<E>, RandomAccessJsr, CloneableJsr {
    private static final long serialVersionUID = 1L;

    private static final JsObjData S = 
        new JsObjData("vjo.java.util.ArrayList", ArrayListJsr.class, "ArrayList");

    
    public static class ResourceSpec {
        public static IComponentSpec getInstance() {
            return S.getResourceSpec(); 
        }
        public static final JsResource RESOURCE = S.getJsResource();
        public static final IJsResourceRef REF = S.getJsResourceRef();
    }

    public static final IComponentSpec SPEC = S.getResourceSpec()
        .addDependentComponent(IllegalArgumentExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(MathJsr.ResourceSpec.getInstance())
        .addDependentComponent(SystemJsr.ResourceSpec.getInstance())
        .addDependentComponent(ArrayJsr.ResourceSpec.getInstance())
        .addDependentComponent(IndexOutOfBoundsExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(IntegerJsr.ResourceSpec.getInstance())
        .addDependentComponent(UtilJsr.ResourceSpec.getInstance())
        .addDependentComponent(ObjectUtilJsr.ResourceSpec.getInstance())
        .addDependentComponent(ArrayIndexOutOfBoundsExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.CollectionJsr.ResourceSpec.getInstance())
        .addDependentComponent(ListJsr.ResourceSpec.getInstance())
        .addDependentComponent(RandomAccessJsr.ResourceSpec.getInstance())
        .addDependentComponent(CloneableJsr.ResourceSpec.getInstance())
        .addDependentComponent(AbstractListJsr.ResourceSpec.getInstance());

    protected ArrayListJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
        super(cmpMeta, isInstance, args);
    }
}