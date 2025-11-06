; 1.3
(defn my-map [f coll]
  (reduce (fn [acc x] (conj acc (f x))) 
          [] 
          coll))

(defn my-filter [pred coll]
  (reduce (fn [acc x]
            (if (pred x)
              (conj acc x)
              acc))
          '()
          coll))