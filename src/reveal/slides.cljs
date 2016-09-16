(ns reveal.slides)

(def slide-1
  [:section
   [:ul
    [:li "Das"]
    [:li "ist"]
    [:li "für"]
    [:li "mich"]
    [:li "Abfall"]]])

(def slide-2
  [:section "Dies ist der Tag, an dem ihr Capt'n Jack Sparrow fast gefangen hättet!"])

(defn all
  "Add here all slides you want to see in your presentation."
  []
  [slide-1 slide-2])