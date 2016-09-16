(ns reveal-cljs.core
  (:require-macros [hiccups.core :as hiccups :refer [html]])
  (:require [hiccups.runtime :as hiccupsrt]))

(enable-console-print!)

(println "This text is printed from src/reveal-cljs/core.cljs. Go ahead and edit it and see reloading in action.")

(defn main []
  (let [slides (html [:section "Ahoi"])]
    (.appendChild (.getElementById js/document "app") slides)))

;; define your app data so that it doesn't get over-written on reload
(defonce app-state (atom {:text "Hello world!"}))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
