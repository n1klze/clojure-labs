(ns lab3-2)

(defn pfilter-lazy
  [pred coll block-size] 
  (let [chunks (partition-all block-size coll)] 
    (mapcat identity 
            (pmap #(doall (filter pred %)) chunks))))