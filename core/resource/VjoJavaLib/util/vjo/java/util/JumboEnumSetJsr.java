package vjo.java.util;

import org.ebayopensource.dsf.aggregator.jsref.JsObj;
import org.ebayopensource.dsf.aggregator.jsref.internals.JsCmpMeta;
import org.ebayopensource.dsf.aggregator.jsref.JsObjData;
import org.ebayopensource.dsf.spec.component.IComponentSpec;
import org.ebayopensource.dsf.resource.pattern.js.JsResource;
import org.ebayopensource.dsf.resource.pattern.js.IJsResourceRef;
import vjo.java.lang.LongJsr;
import vjo.java.lang.IllegalStateExceptionJsr;
import vjo.java.lang.ClassCastExceptionJsr;
import vjo.java.lang.EnumJsr;
import vjo.java.util.IteratorJsr;
import vjo.java.util.NoSuchElementExceptionJsr;
import vjo.java.util.CollectionJsr;
import vjo.java.lang.ClassUtilJsr;
import vjo.java.util.EnumSetJsr;
import org.ebayopensource.dsf.aggregator.jsref.JsEnum;

@org.ebayopensource.dsf.resource.utils.CodeGen("JsrGenerator")
public class JumboEnumSetJsr<E extends EnumJsr> extends EnumSetJsr<E> {
    private static final long serialVersionUID = 1L;

    private static final JsObjData S = 
        new JsObjData("vjo.java.util.JumboEnumSet", JumboEnumSetJsr.class, "JumboEnumSet");

    
    public static class ResourceSpec {
        public static IComponentSpec getInstance() {
            return S.getResourceSpec(); 
        }
        public static final JsResource RESOURCE = S.getJsResource();
        public static final IJsResourceRef REF = S.getJsResourceRef();
    }

    public static final IComponentSpec SPEC = S.getResourceSpec()
        .addDependentComponent(LongJsr.ResourceSpec.getInstance())
        .addDependentComponent(IllegalStateExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(ClassCastExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(EnumJsr.ResourceSpec.getInstance())
        .addDependentComponent(IteratorJsr.ResourceSpec.getInstance())
        .addDependentComponent(NoSuchElementExceptionJsr.ResourceSpec.getInstance())
        .addDependentComponent(CollectionJsr.ResourceSpec.getInstance())
        .addDependentComponent(ClassUtilJsr.ResourceSpec.getInstance())
        .addDependentComponent(vjo.java.util.ArraysJsr.ResourceSpec.getInstance())
        .addDependentComponent(EnumSetJsr.ResourceSpec.getInstance());

    protected JumboEnumSetJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
        super(cmpMeta, isInstance, args);
    }

    public class EnumSetIteratorJsr<E extends JsEnum> extends JsObj implements IteratorJsr<E> {
        private static final long serialVersionUID = 1L;


        protected EnumSetIteratorJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
            super(cmpMeta, isInstance, args);
        }
    }
}