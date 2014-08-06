(defproject pick-a-do "0.1.0-SNAPSHOT"
  :description "FIXME: write pick-a-do description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/math.numeric-tower "0.0.4"]]
  :plugins [[lein-ring "0.8.10"]]
  :profiles
  {:dev {:dependencies [[midje "1.6.3"]]
         :plugins [[lein-midje "3.1.3"]]}})
