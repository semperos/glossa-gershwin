/**
 * Copyright (c) Rich Hickey. All rights reserved.
 * The use and distribution terms for this software are covered by the
 * Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 * which can be found in the file epl-v10.html at the root of this distribution.
 * By using this software in any fashion, you are agreeing to be bound by
 * the terms of this license.
 * You must not remove this notice, or any other, from this software.
 */

package com.semperos.glossa;

import java.io.PushbackReader;
import java.io.Reader;
import java.io.LineNumberReader;
import java.io.IOException;


public class LineNumberingPushbackReader extends PushbackReader{

    // This class is a PushbackReader that wraps a LineNumberReader. The code
    // here to handle line terminators only mentions '\n' because
    // LineNumberReader collapses all occurrences of CR, LF, and CRLF into a
    // single '\n'.

    private static final int newline = (int) '\n';

    private boolean _atLineStart = true;
    private boolean _prev;
    private int _columnNumber = 1;

    public LineNumberingPushbackReader(Reader r){
	super(new LineNumberReader(r));
    }

    /**
     * Only change from Clojure's implementation. We overload the use of ':'
     * to be the start of a word definition (in which case it is followed by
     * a space) as well as for Clojure's keywords. We need to allow a
     * {@link PushbackReader} with a buffer size of 2 to be able to read this.
     *
     * Barring a particular reason not to, we'll just make the size of the
     * {@link LineNumberReader} and this {@link PushbackReader} the same.
     */
    public LineNumberingPushbackReader(Reader r, int size){
	super(new LineNumberReader(r, size), size);
    }

    public int getLineNumber(){
	return ((LineNumberReader) in).getLineNumber() + 1;
    }

    public int getColumnNumber(){
	return _columnNumber;
    }

    public int read() throws IOException{
        int c = super.read();
        _prev = _atLineStart;
        if((c == newline) || (c == -1))
            {
                _atLineStart = true;
                _columnNumber = 1;
            }
        else
            {
                _atLineStart = false;
                _columnNumber++;
            }
        return c;
    }

    public void unread(int c) throws IOException{
        super.unread(c);
        _atLineStart = _prev;
        _columnNumber--;
    }

    public String readLine() throws IOException{
        int c = read();
        String line;
        switch (c) {
        case -1:
            line = null;
            break;
        case newline:
            line = "";
            break;
        default:
            String first = String.valueOf((char) c);
            String rest = ((LineNumberReader)in).readLine();
            line = (rest == null) ? first : first + rest;
            _prev = false;
            _atLineStart = true;
            _columnNumber = 1;
            break;
        }
        return line;
    }

    public boolean atLineStart(){
        return _atLineStart;
    }
}
