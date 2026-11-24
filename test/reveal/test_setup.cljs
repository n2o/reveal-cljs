(ns reveal.test-setup
  "Provides a minimal browser environment for testing the reveal-cljs wrapper
  under Node.

  reveal.core runs `main` as soon as it is loaded and talks to the globals
  `document`, `window`, `Reveal` and `RevealNotes`. This namespace installs
  small fakes for them. It must be required BEFORE reveal.core.

  reveal.js itself is deliberately not involved: `Reveal` is a recording stub,
  so the tests only verify what the wrapper does with it.")

(def slides-element
  "Stands in for <div id=\"slides\">. Only `innerHTML` is used by the wrapper."
  #js {:innerHTML ""})

(set! (.-document js/global)
      #js {:getElementById (fn [id] (when (= id "slides") slides-element))})

(set! (.-window js/global) #js {})

(def calls
  "Log of calls made to the Reveal stub, oldest first. Each entry is a map with
  :fn, :args and (for :initialize) :html - the content of #slides at call time."
  (atom []))

(def config
  "Behaviour of the stub; tests change this with `reset-reveal!`."
  (atom {:ready? false :state nil :speaker-notes? false}))

(defn reset-reveal!
  "Clear the call log and set the stub's behaviour (merged over the defaults)."
  ([] (reset-reveal! {}))
  ([overrides]
   (reset! calls [])
   (reset! config (merge {:ready? false :state nil :speaker-notes? false}
                         overrides))
   (set! (.-innerHTML slides-element) "")))

(defn- record! [f args & {:as extra}]
  (swap! calls conj (merge {:fn f :args (vec args)} extra)))

(set! (.-Reveal js/global)
      #js {:isReady        (fn [] (:ready? @config))
           :getState       (fn [] (:state @config))
           :setState       (fn [state] (record! :set-state [state]))
           :isSpeakerNotes (fn [] (:speaker-notes? @config))
           :dispatchEvent  (fn [event] (record! :dispatch-event [event]))
           :initialize     (fn [options]
                             (record! :initialize [options]
                                      :html (.-innerHTML slides-element))
                             (js/Promise.resolve nil))})

(set! (.-RevealNotes js/global) #js {:id "notes-plugin-stub"})
