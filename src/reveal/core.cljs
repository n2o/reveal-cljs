(ns reveal.core
  (:require-macros [hiccups.core :as hiccups :refer [html]])
  (:require [clojure.string :refer [join]]
            [goog.dom :as gdom]
            [hiccups.runtime :as hiccupsrt]
            [reveal.slides :as slides]))


;; When changing comments, you manually need to refresh your browser
(def options (clj->js {:controls    true
                       :progress    true
                       :transition  "fade"                    ; e.g. none/fade/slide/convex/concave/zoom
                       :slideNumber false
                       :dependencies  [{:src "node_modules/reveal.js/plugin/notes/notes.js"
                                        :async true}]}))


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
  (.setState js/Reveal (.getState js/Reveal)))
(main)

(defn on-js-reload []
  (main))
