(ns lab3-1)

(defn split-into-n-parts
  [coll n]
  (let [cnt       (count coll)
        base-size (quot cnt n)
        rest      (mod cnt n)]
    (loop [c   coll
           idx 0
           acc []]
      (if (< idx n)
        (let [size (+ base-size (if (< idx rest) 1 0))
              part (take size c)]
          (recur (drop size c) (inc idx) (conj acc part)))
        acc))))

(defn pfilter
  ([pred coll] (pfilter pred coll 2))
  ([pred coll n]
   (->> (split-into-n-parts coll n)
        (map #(future (doall (filter pred %))))
        (mapcat deref))))