(ns reveal.core-test
  "Tests for the ClojureScript wrapper (reveal.core / reveal.slides).

  They check that slides defined as Hiccup data are turned into HTML, put into
  the page and handed over to Reveal. reveal.js itself is not tested; it is
  replaced by a recording stub (see reveal.test-setup)."
  (:require [cljs.test :refer-macros [deftest is testing async use-fixtures]]
            [clojure.string :as str]
            [reveal.test-setup :as env]
            [reveal.core :as core]
            [reveal.slides :as slides]))

(use-fixtures :each {:before env/reset-reveal!})

(defn- top-level-sections
  "Number of <section> elements that are not nested in another <section>.
  Returns nil if the opening and closing tags are not balanced."
  [html]
  (loop [tokens (re-seq #"</?section\b" html)
         depth 0
         top 0]
    (if-let [tok (first tokens)]
      (if (= tok "</section")
        (when (pos? depth) (recur (rest tokens) (dec depth) top))
        (recur (rest tokens) (inc depth) (if (zero? depth) (inc top) top)))
      (when (zero? depth) top))))

(defn- initialize-calls []
  (filter #(= :initialize (:fn %)) @env/calls))

(deftest convert-renders-given-slides
  (testing "every slide becomes one HTML string, in order"
    (with-redefs [slides/all (fn [] [[:section [:h1 "A"]]
                                     [:section [:h1 "B"]]])]
      (is (= "<section><h1>A</h1></section><section><h1>B</h1></section>"
             (core/convert))))))

(deftest convert-keeps-vertical-slides-nested
  (with-redefs [slides/all (fn [] [[:section
                                    [:section [:h2 "top"]]
                                    [:section [:h2 "bottom"]]]
                                   [:section [:h2 "next"]]])]
    (is (= 2 (top-level-sections (core/convert))))))

(deftest convert-empty-presentation
  (with-redefs [slides/all (fn [] [])]
    (is (= "" (core/convert)))))

(deftest convert-renders-attributes-and-classes
  (with-redefs [slides/all (fn [] [[:section {:data-auto-animate "data-auto-animate"}
                                    [:i.fas.fa-circle {:data-id "x"}]]])]
    (let [html (core/convert)]
      (is (str/includes? html "data-auto-animate=\"data-auto-animate\""))
      (is (str/includes? html "class=\"fas fa-circle\""))
      (is (str/includes? html "data-id=\"x\"")))))

(deftest shipped-slides-render
  (testing "the slides that ship with the template render to well-formed HTML"
    (let [slide-list (slides/all)
          html       (core/convert)]
      (is (seq slide-list) "slides/all must return at least one slide")
      (is (every? #(and (vector? %) (= :section (first %))) slide-list)
          "every top-level slide has to be a [:section ...] vector")
      (is (not (str/blank? html)))
      (is (= (count slide-list) (top-level-sections html))
          "one top-level <section> per slide, tags balanced"))))

(deftest main-puts-slides-into-the-page-and-initializes-reveal
  (async done
    (-> (core/main)
        (.then
         (fn []
           (is (= (core/convert) (.-innerHTML env/slides-element))
               "#slides contains the converted slides")
           (let [inits (initialize-calls)]
             (is (= 1 (count inits)) "Reveal is initialized exactly once")
             (is (not (str/blank? (:html (first inits))))
                 "the slides are in the page BEFORE Reveal is initialized")
             (let [opts (first (:args (first inits)))]
               (is (identical? core/options opts)
                   "Reveal gets the options defined in reveal.core")
               (is (= [(.-RevealNotes js/global)] (vec (.-plugins opts)))
                   "the notes plugin is registered"))))
         )
        (.catch (fn [e] (is false (str "main failed: " e))))
        (.finally done))))

(deftest main-announces-resume-to-speaker-notes
  (async done
    (-> (core/main)
        (.then
         (fn []
           (let [events (filter #(= :dispatch-event (:fn %)) @env/calls)]
             (is (= 1 (count events)))
             (is (= "resumed" (.-type (first (:args (first events)))))))))
        (.catch (fn [e] (is false (str "main failed: " e))))
        (.finally done))))

(deftest main-does-not-resume-in-speaker-notes-view
  (async done
    (env/reset-reveal! {:speaker-notes? true})
    (-> (core/main)
        (.then
         (fn []
           (is (empty? (filter #(= :dispatch-event (:fn %)) @env/calls))
               "no resumed event in the speaker notes window")))
        (.catch (fn [e] (is false (str "main failed: " e))))
        (.finally done))))

(deftest main-restores-position-when-rerendering
  (testing "on a reload (Reveal already running) the previous state is restored"
    (async done
      (let [state #js {:indexh 2 :indexv 1}]
        (env/reset-reveal! {:ready? true :state state})
        (-> (core/main)
            (.then
             (fn []
               (let [fns (map :fn @env/calls)]
                 (is (= [:initialize :set-state :dispatch-event] fns)
                     "initialize, then setState, then resumed"))
               (is (identical? state
                               (first (:args (first (filter #(= :set-state (:fn %))
                                                            @env/calls))))))))
            (.catch (fn [e] (is false (str "main failed: " e))))
            (.finally done))))))

(deftest main-does-not-set-state-on-first-render
  (async done
    (-> (core/main)
        (.then
         (fn []
           (is (empty? (filter #(= :set-state (:fn %)) @env/calls)))))
        (.catch (fn [e] (is false (str "main failed: " e))))
        (.finally done))))
