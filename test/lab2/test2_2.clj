(ns test2-2
  (:require [clojure.test :refer [deftest is]]
            [lab2-2 :as lazy]))

(defn approx=
  "Проверяет, что a и b равны с точностью eps."
  ([a b] (approx= a b 1e-2))
  ([a b eps] (<= (Math/abs (- a b)) eps)))

(def f-x (fn [x] x))
(def f-x2 (fn [x] (* x x)))
(def f-sin (fn [x] (Math/sin x)))

(deftest test-integrate-linear
  (let [actual ((lazy/integral-lazy f-x) 1)
        expected 0.5]
    (is (approx= actual expected))))

(deftest test-integrate-linear-negative
  (let [actual ((lazy/integral-lazy f-x) -1)
        expected -0.5]
    (is (approx= actual expected))))

(deftest test-integrate-quadratic
  (let [actual ((lazy/integral-lazy f-x2) 3)
        expected 9.0]
    (is (approx= actual expected))))

(deftest test-integrate-sin
  (let [actual ((lazy/integral-lazy f-sin) Math/PI)
        expected 2.0]
    (is (approx= actual expected))))

(deftest test-integrate-linear-on-a-big-argument
  (let [actual ((lazy/integral-lazy f-x) 10000)
        expected 50000000.0]
    (is (approx= actual expected))))