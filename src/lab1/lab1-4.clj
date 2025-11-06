; 1.4
(defn get-valid-chars [string alphabet]
  (filter (fn [x] (not= x (last string))) 
          alphabet))

(defn append-string [string alphabet]
  (map (fn [x] (str string x))
       (get-valid-chars string alphabet)))

(defn expand-strings [strings alphabet]
  (reduce concat
          (map (fn [string]
                 (append-string string alphabet))
               strings)))

(defn gen-strings [alphabet n]
  (if (= 0 n)
    '("")
    (let [short (gen-strings alphabet (dec n))]
      (expand-strings short alphabet))))


(let [chars [\a \b \c]
      n 2]
  (println "1.4:" (gen-strings chars n)))