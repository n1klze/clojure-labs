(ns test3-2
  (:require [clojure.test :refer [deftest is]]
            [lab3-2 :refer [pfilter-lazy]]))

(defn clock
  "Показывает, сколько времени заняло выполнение функции func в ms."
  [func]
  (let [t0 (System/nanoTime)]
    (func)
    (let [t1 (System/nanoTime)]
      (/ (- t1 t0) 1e6))))

(defn slow-pred [x] (Thread/sleep 1) (even? x))

(def data (range))

(deftest correct-filtering-test
  (let [chunk-size            10
        lazy-filter-result    (take 500 (pfilter-lazy slow-pred data chunk-size))
        regular-filter-result (take 500 (filter slow-pred data))]
    (is (= (set lazy-filter-result) (set regular-filter-result)))))

(deftest performance-test
  (let [chunk-size          10
        lazy-filter-time    (clock #(doall (take 500 (pfilter-lazy slow-pred data chunk-size))))
        regular-filter-time (clock #(doall (take 500 (filter slow-pred data))))]
    (println "lazy-filter time:" lazy-filter-time "ms.")
    (println "regular filter time:" regular-filter-time "ms.")
    (println "lazy-filter is" (/ regular-filter-time lazy-filter-time) "times faster than regular filter")
    (is (< lazy-filter-time regular-filter-time))))