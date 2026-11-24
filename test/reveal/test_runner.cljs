(ns reveal.test-runner
  (:require [cljs.test :as t]
            [reveal.test-setup]
            [reveal.core-test]))

(defmethod t/report [:cljs.test/default :end-run-tests] [m]
  (js/process.exit (if (t/successful? m) 0 1)))

(defn -main [& _]
  (t/run-tests 'reveal.core-test))

(set! *main-cli-fn* -main)
