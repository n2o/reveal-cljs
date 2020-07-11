(ns reveal.core
  (:require-macros [hiccups.core :refer [html]])
  (:require [clojure.string :refer [join]]
            [goog.dom :as gdom]
            [hiccups.runtime]
            [reveal.slides :as slides]))


;; When changing comments, you manually need to refresh your browser
(def options (clj->js {:hash true
                       :controls true
                       :controlsTutorial true
                       :progress false
                       :transition "fade"                   ; e.g. none/fade/slide/convex/concave/zoom
                       :slideNumber "c"
                       :plugins [js/RevealNotes]}))


;; -----------------------------------------------------------------------------
;; You do not need to change anything below this comment

(defn convert
  "Get list of all slides and convert them to html strings."
  []
  (let [slides (slides/all)]
    (join (map #(html %) slides))))

(defn main
  "Get all slides, set them as innerHTML and reinitialize Reveal.js"
  []
  (set! (.. (gdom/getElement "slides") -innerHTML) (convert))
  (.initialize js/Reveal options)
  (.setState js/Reveal (.getState js/Reveal)))
(main)
