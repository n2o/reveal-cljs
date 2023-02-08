(ns reveal.slides)

(def slide-1
  [:section
   [:h1 "reveal-cljs"]
   [:h5 "Create awesome Web-Presentations with ClojureScript"]
   [:p "Based on "
    [:a {:href "http://lab.hakim.se/reveal-js/"} "reveal.js"]]
   [:aside.notes
    [:ul [:li "Some notes"]]]])

(def ^:private argument
  [:div.argument
   [:i.mr.statement.fas.fa-circle {:data-id "premise"}]
   [:i.mr.pro.fas.fa-long-arrow-alt-right {:data-id "relation"}]
   [:i.statement.fas.fa-circle {:data-id "conclusion"}]])

(def slide-auto-animate
  [:section
   [:section {:data-auto-animate "data-auto-animate"}
    [:h2 "Auto Animate"]
    [:p "Automatically animate CSS / SVGs on a slide"
     [:small "Press " [:code "space"] " for next animation step"]]
    (last argument)]
   [:section {:data-auto-animate "data-auto-animate"}
    argument]
   [:section {:data-auto-animate "data-auto-animate"}
    [:i.statement.fas.fa-circle {:data-id "conclusion"}]
    [:div.row
     [:div.offset-4.col-2
      [:i.relation.pro.fas.fa-long-arrow-alt-right {:data-id "relation" :data-fa-transform "rotate--40"}]]
     [:div.col-2
      [:i.relation.con.fas.fa-long-arrow-alt-left {:data-id "relation-con" :data-fa-transform "rotate-40"}]]]
    [:div.row
     [:div.offset-3.col-2
      [:i.statement.fas.fa-circle {:data-id "premise"}]]
     [:div.offset-2.col-2
      [:i.statement.fas.fa-circle {:data-id "premise-con"}]]]]
   [:section {:data-auto-animate "data-auto-animate"}
    [:div.row
     [:div.col-12
      [:i.issue.fas.fa-circle {:data-id "issue"}]]]
    [:div.row
     [:div.col-12
      [:i.relation.issue.fas.fa-long-arrow-alt-up {:data-id "relation-position"}]]]
    [:div.row
     [:div.col-12
      [:i.statement.fas.fa-circle {:data-id "conclusion"}]]]
    [:div.row
     [:div.offset-4.col-2
      [:i.relation.pro.fas.fa-long-arrow-alt-right {:data-id "relation" :data-fa-transform "rotate--40"}]]
     [:div.col-2
      [:i.relation.con.fas.fa-long-arrow-alt-left {:data-id "relation-con" :data-fa-transform "rotate-40"}]]]
    [:div.row
     [:div.offset-3.col-2
      [:i.statement.fas.fa-circle {:data-id "premise"}]]
     [:div.offset-2.col-2
      [:i.statement.fas.fa-circle {:data-id "premise-con"}]]]]])

(def slide-2
  [:section
   [:section
    [:h2 "Vertical Slides"]
    [:p "Generate your slides "
     [:a {:href "https://github.com/teropa/hiccups"} "with Hiccups"]]]
   [:section
    [:h2 "Tutorial"]
    [:p "Watch the full tutorial of reveal.js "
     [:a {:href "https://revealjs.com"} "on revealjs.com"]]]])

(defn all
  "Add here all slides you want to see in your presentation."
  []
  [slide-1
   slide-auto-animate
   slide-2])