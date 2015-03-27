(defproject pi-taster "0.1.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [org.clojure/data.json "0.2.5"]
                 [net.mikera/vectorz-clj "0.29.0"]
                 ]
  :aot [pi-taster.core]
  :main pi-taster.core
  )
