package com.semperos.glossa;

import clojure.lang.IFn;
import clojure.lang.LineNumberingPushbackReader;
import clojure.lang.Namespace;
import clojure.lang.RT;
import clojure.lang.Symbol;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Old-school debug REPL taken from commented-out Clojure main in LispReader
 */
public class main {
    final static private IFn IN_NS = ClojureApi.var("clojure.core", "in-ns");
    final static private IFn IMPORT = ClojureApi.var("clojure.core", "import");
    final static private Symbol STACK_CLASS_SYM = Symbol.intern("GlossaStack");
    final static private Symbol GLOSSA_NS_SYM = Symbol.intern("glossa.core");
    final static private Namespace GLOSSA_NS = Namespace.findOrCreate(GLOSSA_NS_SYM);

    public static void main(String[] args) {
	LineNumberingPushbackReader r = new LineNumberingPushbackReader(new InputStreamReader(System.in));
	OutputStreamWriter w = new OutputStreamWriter(System.out);
	Object ret = null;
        // @todo Make sure clojure.core is available in glossa.core.
        // @todo Work on how GlossaStack operations themselve affect the stack (e.g., seq())
        IN_NS.invoke(GLOSSA_NS_SYM);
        GLOSSA_NS.importClass(STACK_CLASS_SYM, GlossaStack.class);
	try {
            for(; ;) {
                ret = GlossaCompiler.load(r);
                RT.print(ret, w);
                w.write('\n');
                if(ret != null)
                    w.write(ret.getClass().toString());
                w.write('\n');
                w.flush();
            }
        }
	catch(Exception e) {
            e.printStackTrace();
        }
    }
}
