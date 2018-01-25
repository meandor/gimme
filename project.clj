(defproject com.github.meandor/gimme "0.1.1"
  :description "Database config abstraction layer component"
  :url "https://github.com/meandor/gimme"
  :license {:name "Apache Version 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0"}
  :scm {:name "git"
        :url  "https://github.com/meandor/gimme"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/tools.logging "0.4.0"]
                 [com.taoensso/carmine "2.17.0"]
                 [com.stuartsierra/component "0.3.2"]]

  :lein-release {:deploy-via :clojars}
  :profiles {:dev {:plugins      [[lein-release/lein-release "1.0.9"]]
                   :dependencies [[com.github.kstyrc/embedded-redis "0.6"]]}})
