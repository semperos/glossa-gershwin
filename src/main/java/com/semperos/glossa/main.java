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
        // @todo Work on how GlossaStack operations themselves affect the stack (e.g., seq())
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
                    if(!firstPass) {
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
                    w.write(REPL_PROMPT);
                    w.flush();
                } else {
                    r.unread(ch);
                }
                ret = GlossaParser.read(r, true, null, false);
                GlossaCompiler.eval(ret);
            }
        }
	catch(Exception e) {
            e.printStackTrace();
        }
    }
}
