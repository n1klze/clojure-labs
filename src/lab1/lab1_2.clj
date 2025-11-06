; 1.2
(defn get-valid-chars [string alphabet]
  (let [invalid-char (last string)]
    (loop [chars alphabet
           acc   '()]
      (if (empty? chars)
        acc
        (let [current (first chars)
              rest    (rest chars)]
          (if (= current invalid-char)
            (recur rest acc)
            (recur rest (conj acc current))))))))

(defn append-string [string alphabet]
  (let [valid-chars (get-valid-chars string alphabet)]
      (loop [chars valid-chars
             acc   '()]
        (if (empty? chars)
          acc
          (let [current (first chars)
                rest    (rest chars)]
            (recur rest 
                   (conj acc (str string current))))))))

(defn expand-strings [strings alphabet]
  (loop [list strings
         acc  '()]
    (if (empty? list)
      acc
      (let [string (first list)
            rest   (rest list)
            new-strings (append-string string alphabet)]
        (recur rest (concat acc new-strings))))))

(defn gen-strings [alphabet n]
  (loop [len n
         acc '("")]
    (if (= 0 len)
      acc
      (recur (dec len) (expand-strings acc alphabet)))))

(let [chars [\a \b \c]
      n 2]
  (println "1.2:" (gen-strings chars n)))