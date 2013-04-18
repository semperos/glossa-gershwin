(defproject glossa "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :source-paths ["src/main/clj"]
  :java-source-paths ["src/main/java"]
  :javac-options     ["-target" "1.7" "-source" "1.7"]
  :test-paths ["src/test/clj"]
  :dependencies [[org.clojure/clojure "1.6.0-master-SNAPSHOT"]]
  :main com.semperos.glossa.main)
