(ns reveal.core
  (:require-macros [hiccups.core :as hiccups :refer [html]])
  (:require [hiccups.runtime :as hiccupsrt]))
(enable-console-print!)

(defn main
  "Get all slides, set them as innerHTML and reinitialize Reveal.js"
  []
  (let [slides (html [:div.slides [:section "Ahoiasdasda"]])]
    (set! (.. (.getElementById js/document "app") -innerHTML) slides))
  (.initialize js/Reveal))
(main)

;; define your app data so that it doesn't get over-written on reload
(defonce app-state (atom {:text "Hello world!"}))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  (main)
)
