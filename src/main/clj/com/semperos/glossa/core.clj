(ns glossa.core
  (:refer-clojure :exclude [read-string])
  (:import [com.semperos.glossa GlossaParser GlossaRT]))

(defn read-string
  [^String s]
  (GlossaRT/readString s))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))
  (println "Hello, World!"))
