(ns reveal.slides)

(def slide-1
  [:section
   [:h1 "reveal-cljs"]
   [:h5 "Create awesome Web-Presentations with ClojureScript"]
   [:p "Based on "
    [:a {:href "http://lab.hakim.se/reveal-js/"} "reveal.js"]]])

(def slide-2
  [:section
   [:section
    [:h2 "Vertical Slides"]
    [:p "Generate your slides "
     [:a {:href "https://github.com/teropa/hiccups"} "with Hiccups"]]]
   [:section
    [:h2 "Tutorial"]
    [:p "Watch the full tutorial of reveal.js "
     [:a {:href "https://github.com/hakimel/reveal.js/blob/master/demo.html"} "on this site"]]]])

(defn all
  "Add here all slides you want to see in your presentation."
  []
  [slide-1
   slide-2])
