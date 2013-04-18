package com.semperos.glossa;

import clojure.lang.LispReader;
import clojure.lang.RT;
import clojure.lang.SeqEnumeration;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Old-school debug REPL taken from commented-out Clojure main in LispReader
 */
public class main {
    private static final String REPL_PROMPT = "glossa.core> ";

    public static void main(String[] args) {
	LineNumberingPushbackReader r = new LineNumberingPushbackReader(new InputStreamReader(System.in), 2);
	OutputStreamWriter w = new OutputStreamWriter(System.out);
	Object ret = null;
        // @todo Make sure clojure.core is available in glossa.core.
        // @todo Work on how GlossaStack operations themselve affect the stack (e.g., seq())
        GlossaRT.doInit();
        boolean firstPass = true;
	try {
            for(; ;) {
                if(firstPass) {
                    w.write(REPL_PROMPT);
                    w.flush();
                    firstPass = false;
                }
                int ch = LispReader.read1(r);
                if (ch == 10) {
                    w.write(REPL_PROMPT);
                    w.flush();
                } else {
                    r.unread(ch);
                }
                // ret = GlossaCompiler.load(r);
                ret = GlossaParser.read(r, true, null, false);
                GlossaCompiler.eval(ret);
                // RT.print(ret, w);
                // w.write('\n');
                // if(ret != null)
                //     w.write(ret.getClass().toString());
                w.write("\n--- Data Stack:\n");
                SeqEnumeration iter = new SeqEnumeration(GlossaStack.seq());
                while (iter.hasMoreElements()) {
                    RT.print(iter.nextElement(), w);
                    // w.write(iter.nextElement().toString());
                    w.write('\n');
                }
                // w.write(GlossaStack.seq().toString());
                w.write('\n');
                w.flush();
            }
        }
	catch(Exception e) {
            e.printStackTrace();
        }
    }
}
