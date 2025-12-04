; 2
(defn integral
  ([f] (integral f 1e-3))
  ([f h]
   (fn [x]
     (let [sign   (if (neg? x) -1 1)
           x      (* sign x)
           n      (Math/ceil (/ x h))
           xs     (map #(* % h) (range 1 n))
           sum-fx (reduce + (map f xs))]
       (* sign h
          (+ (/ (+ (f 0) (f x)) 2) sum-fx))))))

(println ((integral (fn [x] (* x x))) -1))
