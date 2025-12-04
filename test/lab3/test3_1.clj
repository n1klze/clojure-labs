(ns test3-1
  (:require [clojure.test :refer [deftest is]]
            [lab3-1 :as future]))

(defn slow-pred [x] (Thread/sleep 1) (even? x))
(defn fast-pred [x] (even? x))

(def data (range 1000))

(defn clock
  "Показывает, сколько времени заняло выполнение функции func в ms."
  [func]
  (let [t0 (System/nanoTime)]
    (func)
    (let [t1 (System/nanoTime)]
      (/ (- t1 t0) 1e6))))

(deftest correct-filtering-test
  (let [future-filter-result (future/pfilter fast-pred data)
        regular-filter-result (filter fast-pred data)]
    (is (= (set future-filter-result) (set regular-filter-result)))))

(deftest performance-test
  (let [number-of-blocks    4
        future-filter-time  (clock #(doall (future/pfilter slow-pred data number-of-blocks)))
        regular-filter-time (clock #(doall (filter slow-pred data)))]
    (println "future-filter time:" future-filter-time "ms.")
    (println "regular filter time:" regular-filter-time "ms.")
    (println "amount of blocks:" number-of-blocks)
    (println "future-filter is" (/ regular-filter-time future-filter-time) "times faster than regular filter")
    (is (< future-filter-time regular-filter-time))))