package com.semperos.glossa;

import clojure.lang.LineNumberingPushbackReader;
import clojure.lang.RT;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Old-school debug REPL taken from commented-out Clojure main in LispReader
 */
public class main {
    public static void main(String[] args) {
	LineNumberingPushbackReader r = new LineNumberingPushbackReader(new InputStreamReader(System.in));
	OutputStreamWriter w = new OutputStreamWriter(System.out);
	Object ret = null;
	try {
            for(; ;) {
                ret = GlossaParser.read(r, true, null, false);
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
