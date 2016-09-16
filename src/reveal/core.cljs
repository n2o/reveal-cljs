(ns reveal.core
  (:require-macros [hiccups.core :as hiccups :refer [html]])
  (:require [clojure.string :refer [join]]
            [goog.dom :as gdom]
            [hiccups.runtime :as hiccupsrt]
            [reveal.slides :as slides]))

(def options #js {:controls    true
                  :progress    true
                  :transition  "default"                    ; e.g. none/fade/slide/convex/concave/zoom
                  :slideNumber false})


;;;; You do not need to change anything below this comment

(defn convert
  "Get list of all slides and convert them to html strings."
  []
  (let [slides (slides/all)]
    (join (map #(html %) slides))))

(defn main
  "Get all slides, set them as innerHTML and reinitialize Reveal.js"
  []
  (set! (.. (.getElementById js/document "slides") -innerHTML) (convert))
  (.initialize js/Reveal options)
  (.right js/Reveal)                                        ; Dirty workaround...
  (.left js/Reveal))
(main)

(defn on-js-reload []
  (main))
