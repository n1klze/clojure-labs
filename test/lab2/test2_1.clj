(ns test2-1
  (:require [clojure.test :refer [deftest is]]
            [lab2-1 :as memo]))

(defn clock
  "Показывает, сколько времени заняло выполнение функции func в ms."
  [func]
  (let [t0 (System/nanoTime)]
    (func)
    (let [t1 (System/nanoTime)]
      (/ (- t1 t0) 1e6))))

(defn approx=
  "Проверяет, что a и b равны с точностью eps."
  ([a b] (approx= a b 1e-4))
  ([a b eps] (<= (Math/abs (- a b)) eps)))

(def f-x (fn [x] x))
(def f-x2 (fn [x] (* x x)))
(def f-sin (fn [x] (Math/sin x)))

(deftest test-integrate-linear
  (let [actual ((memo/integral-memoized f-x) 1)
        expected 0.5]
    (is (approx= actual expected 1e-2))))

(deftest test-integrate-linear-negative
  (let [actual ((memo/integral-memoized f-x) -1)
        expected -0.5]
    (is (approx= actual expected 1e-2))))

(deftest test-integrate-quadratic
  (let [actual ((memo/integral-memoized f-x2) 3)
        expected 9.0]
    (is (approx= actual expected 1e-2))))

(deftest test-integrate-sin
  (let [actual ((memo/integral-memoized f-sin) Math/PI)
        expected 2.0]
    (is (approx= actual expected 1e-2))))

(deftest test-memoization-saves-time-on-bigger-close-argument
  (let [integral (memo/integral-memoized (fn [x] x))
        time1 (clock #(integral 5))
        time2 (clock #(integral 6))]
    (is (> time1 time2))))

(deftest test-memoization-saves-time-on-the-same-argument
  (let [integral (memo/integral-memoized (fn [x] x))
        time1 (clock #(integral 7))
        time2 (clock #(integral 7))]
    (is (> time1 time2))))

(deftest test-memoization-saves-time-on-smaller-close-argument
  (let [integral (memo/integral-memoized (fn [x] x))
        time1 (clock #(integral 9))
        time2 (clock #(integral 8))]
    (is (> time1 time2))))