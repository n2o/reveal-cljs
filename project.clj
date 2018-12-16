(defproject reveal-cljs "1.0.0"
  :description "A reveal.js wrapper in ClojureScript."
  :url "https://github.com/n2o/reveal-cljs"
  :license {:name "MIT"
            :url  "https://choosealicense.com/licenses/mit/"}

  :min-lein-version "2.6.1"

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.439"]
                 [org.clojure/core.async "0.4.490" :exclusions [org.clojure/tools.reader]]
                 [hiccups "0.3.0"]]

  :plugins [[lein-figwheel "0.5.17"]
            [lein-cljsbuild "1.1.3" :exclusions [[org.clojure/clojure]]]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src"]

                ;; the presence of a :figwheel configuration here
                ;; will cause figwheel to inject the figwheel client
                ;; into your build
                :figwheel {:on-jsload "reveal.core/on-js-reload"
                           ;; :open-urls will pop open your application
                           ;; in the default browser once Figwheel has
                           ;; started and complied your application.
                           ;; Comment this out once it no longer serves you.
                           :open-urls ["http://localhost:3449/index.html"]}

                :compiler {:main reveal.core
                           :asset-path "js/compiled/out"
                           :output-to "resources/public/js/compiled/reveal_cljs.js"
                           :output-dir "resources/public/js/compiled/out"
                           :source-map-timestamp true
                           :parallel-build true
                           ;; To console.log CLJS data-structures make sure you enable devtools in Chrome
                           ;; https://github.com/binaryage/cljs-devtools
                           :preloads [devtools.preload]}}
               ;; This next build is an compressed minified build for
               ;; production. You can build this with:
               ;; lein cljsbuild once min
               {:id "min"
                :source-paths ["src"]
                :compiler {:output-to "resources/public/js/compiled/reveal_cljs.js"
                           :main reveal.core
                           :optimizations :advanced
                           :pretty-print false}}]}

  :figwheel {:css-dirs ["resources/public/css"]}

  :profiles {:dev {:dependencies [[binaryage/devtools "0.9.10"]
                                  [figwheel-sidecar "0.5.17"]
                                  [com.cemerick/piggieback "0.2.2"]]
                   ;; need to add dev source path here to get user.clj loaded
                   :source-paths ["src" "dev"]
                   ;; for CIDER
                   ;; :plugins [[cider/cider-nrepl "0.12.0"]]
                   :repl-options {; for nREPL dev you really need to limit output
                                  :init (set! *print-length* 50)
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}

  ;; Add java.xml.bind if JDK9 is used
  :jvm-opts ~(let [version (System/getProperty "java.version")
                   [major _ _] (clojure.string/split version #"\.")]
               (if (>= (Integer. major) 9)
                 ["--add-modules" "java.xml.bind"]
                 []))
)
