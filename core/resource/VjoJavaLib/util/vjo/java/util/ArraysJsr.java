package vjo.java.util;

import org.ebayopensource.dsf.aggregator.jsref.JsObj;
import org.ebayopensource.dsf.aggregator.jsref.internals.JsCmpMeta;
import org.ebayopensource.dsf.aggregator.jsref.JsObjData;
import org.ebayopensource.dsf.spec.component.IComponentSpec;
import org.ebayopensource.dsf.resource.pattern.js.JsResource;
import org.ebayopensource.dsf.resource.pattern.js.IJsResourceRef;
import vjo.java.lang.DoubleJsr;
import vjo.java.lang.FloatJsr;
import vjo.java.lang.IntegerJsr;
import vjo.java.lang.SystemJsr;
import vjo.java.lang.IllegalArgumentExceptionJsr;
import vjo.java.util.HashSetJsr;
import vjo.java.lang.ArrayIndexOutOfBoundsExceptionJsr;
import vjo.java.lang.NullPointerExceptionJsr;
import vjo.java.util.AbstractListJsr;
import vjo.java.util.RandomAccessJsr;
import vjo.java.lang.ObjectUtilJsr;
import vjo.java.lang.UtilJsr;

@org.ebayopensource.dsf.resource.utils.CodeGen("JsrGenerator")
public class ArraysJsr extends JsObj {
    private static final long serialVersionUID = 1L;

    private static final JsObjData S = 
        new JsObjData("vjo.java.util.Arrays", ArraysJsr.class, "Arrays");

    
    public static class ResourceSpec {
        public static IComponentSpec getInstance() {
            return S.getResourceSpec(); 
        }
        public static final JsResource RESOURCE = S.getJsResource();
        public static final IJsResourceRef REF = S.getJsResourceRef();
    }

    public static final IComponentSpec SPEC = S.getResourceSpec()
        .addDependentComponent(DoubleJsr.ResourceSpec.getInstance())
        .addDependentComponent(FloatJsr.ResourceSpec.getInstance())
        .addDependentComponent(IntegerJsr.ResourceSpec.getInstance())
        .addDependentComponent(SystemJsr.ResourceSpec.getInstance())
        .addDependentComponent(IllegalArgumentExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(HashSetJsr.ResourceSpec.getInstance())
        .addDependentComponent(ArrayIndexOutOfBoundsExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(NullPointerExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(AbstractListJsr.ResourceSpec.getInstance())
        .addDependentComponent(RandomAccessJsr.ResourceSpec.getInstance())
        .addDependentComponent(ObjectUtilJsr.ResourceSpec.getInstance())
        .addDependentComponent(UtilJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.ComparatorJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.ListJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.SetJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.lang.MathJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.lang.reflect.ArrayJsr.ResourceSpec.getInstance());

    protected ArraysJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
        super(cmpMeta, isInstance, args);
    }

    public static class ArrayListJsr<E> extends AbstractListJsr<E> implements RandomAccessJsr {
        private static final long serialVersionUID = 1L;

        private static final JsObjData S = 
            new JsObjData("vjo.java.util.Arrays.ArrayList", ArrayListJsr.class, "Arrays");

        protected ArrayListJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
            super(cmpMeta, isInstance, args);
        }
    }
}