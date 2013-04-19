package com.semperos.glossa;

import clojure.lang.IFn;
import clojure.lang.Keyword;
import clojure.lang.Namespace;
import clojure.lang.Symbol;

public class GlossaRT {
    final static private String GLOSSA_VAR_PREFIX = "__GLS__";
    final static private IFn IN_NS = ClojureApi.var("clojure.core", "in-ns");
    final static private IFn IMPORT = ClojureApi.var("clojure.core", "import");
    final static private IFn REFER = ClojureApi.var("clojure.core", "refer");
    final static private Symbol STACK_CLASS_SYM = Symbol.intern("GlossaStack");
    final static private Symbol CLOJURE = Symbol.intern("clojure.core");
    final static private Symbol GLOSSA = Symbol.intern("glossa.core");
    final static private Namespace GLOSSA_NS = Namespace.findOrCreate(GLOSSA);

    static void doInit() {
        IN_NS.invoke(GLOSSA);
        GLOSSA_NS.importClass(STACK_CLASS_SYM, GlossaStack.class);
        REFER.invoke(CLOJURE);
    }

    public final static Keyword STACK_VOID = Keyword.intern("glossa.core", "stack-void");
}
