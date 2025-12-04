(ns lab2-1)

; 2
(defn integral
  ([f] (integral f 0.1))
  ([f h]
   (fn [x]
     (let [sign   (if (neg? x) -1 1)
           x      (* sign x)
           n      (Math/ceil (/ x h))
           xs     (map #(* % h) (range 1 n))
           sum-fx (reduce + (map f xs))]
       (* sign h
          (+ (/ (+ (f 0) (f x)) 2) sum-fx))))))

; 2.1
(defn trapezoid-area [f x0 x1]
  (double (* (- x1 x0) (/ (+ (f x0) (f x1)) 2))))

(def integral-sums-memoized
  (memoize (fn [f x0 x1 h]
             (if (>= x0 x1)
                0
                (+ (integral-sums-memoized f x0 (- x1 h) h) 
                   (trapezoid-area f (- x1 h) x1))))))


(defn integral-memoized 
  ([f] (integral-memoized f 1/10))
  ([f h]
   (let [integral-fn (memoize
                      (fn [x]
                        (if (neg? x)
                          (- (integral-sums-memoized f 0 (- x) h))
                          (integral-sums-memoized f 0 x h))))]
     integral-fn)))

(defn heavy-function [x]
  (Thread/sleep 1) 
  (* 2.0 x))

(let [F heavy-function
      x 150]
  (println ((integral F) x))
  (println (time ((integral F) x)))
  
  (println ((integral-memoized F) 155))
  (println (time ((integral-memoized F) x))))
