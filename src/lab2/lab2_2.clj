(ns lab2-2)

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

; 2.2
(defn integral-lazy 
  ([f] (integral-lazy f 1/10))
  ([f h]
   (let [xs            (map #(* % h) (range))
         fs            (map f xs)
         areas         (map (fn [a b] (* 0.5 h (+ a b))) fs (rest fs))
         integral-sums (cons 0.0 (reductions + areas))]
     (fn [x]
       (let [sign   (if (neg? x) -1 1)
             x      (* sign x)
             n (long (Math/floor (/ x h)))]
         (* sign (nth integral-sums n)))))))

(defn heavy-function [x]
  (Thread/sleep 1) 
   (* 2.0 x))

(let [F heavy-function
      x 150]
  (println ((integral F) x))
  (println (time ((integral F) x)))

  (println ((integral-lazy F) x))
  (println (time ((integral-lazy F) x))))
