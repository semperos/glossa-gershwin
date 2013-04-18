# Glossa

Language fun.

Here's a working example of adding things to the stack, defining a new word, and using that word. Boot up a Clojure REPL (or write it in Java and use your IDE) and go to town:

```clj
(import 'com.semperos.glossa.GlossaCompiler)
(import 'java.io.StringReader)
;; Add data to the stack
(GlossaCompiler/load (StringReader. "2"))
(GlossaCompiler/load (StringReader. "2"))
;; See what's on the stack
(GlossaStack/seq)

;; Define the 'add' word.
;; Note how Clojure interop is seemless.
(GlossaCompiler/load (StringReader. ": add [] (+ (GlossaStack/peekPopMutable) (GlossaStack/peekPopMutable)) ;"))

;; Evaluate the 'add' word
(GlossaCompiler/load (StringReader. "add"))
;; See what's left on the stack
(GlossaStack/seq)
```

## Clojure Notes ##

These notes are based on Clojure version 1.6.0-master-SNAPSHOT, commit 8be9b2b.

 * Main eval method - Compiler.java:6585
 * Main analyze method - Compiler.java:6325

High-level Clojure evaluation workflow:

 1. Parse input as one of whitespace, a number, a +/- followed by a number, a macro form, or an arbitrary token (Lisp symbols).
 2. Read goes through a single form and returns that. To read through a whole string/stream of input, read must be called in a loop until the end of the input is reached.
 3. The various "load" methods/functions take a given resource (String, stream) and do that looping, calling read on forms and passing that to eval.
 4. The central eval method does macro expansion, function invocation and analysis for other data types.
 5. Analysis takes a given form returned by the reader and analyzes it in the EVAL context to generate the appropriate subclass of the Expr interface. For complex Exprs, an Expr subclass will define an inner Parser class with a parse method that further breaks down how the complex Expr is represented in terms of other Expr's (see ConstantExpr for an example)
 6. A lot of important analysis starts from analyzeSeq, since Clojure is a Lisp and this is th
 7. These Expr subclasses come in groups (literals, assignables, etc.), but the essential methods from the Expr inteface are eval and emit, and most (all?) of the subclasses are also given a val method.
    * The eval method does the type-specific evaluation of the form
    * The val method returns the value of the given expression. For String literals, for example, calling eval() is the same as calling val(), and val() is the same as returning the original String that was passed into the constructor for StringExpr.
    * The emit method encodes how to emit the given data structure as JVM bytecode. This is where, for example, String values are pushed onto the JVM stack.

## Todos / Next Steps ##

We want some auto-use of Clojure's namespacing facilities, since that's what we're using to keep track of things in our "dictionary" of "words." Use something like(.isBound (ClojureApi/var "clojure.core" "+")), with a convention like Glossa "primitives" being named something like "__glossa-" + name of Clojure "primitive" (+/-, for example, are implemented in Java).

## Installation

FIXME

## Usage

FIXME: explanation

    $ java -jar glossa-0.1.0-standalone.jar [args]

## Options

FIXME: listing of options this app accepts.

## Examples

...

### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
