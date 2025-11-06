; 1.1
(defn get-valid-chars [string alphabet]
  (let [invalid-char (last string)]
    (if (empty? alphabet)
      '()
      (let [current (first alphabet)
            rest    (rest alphabet)]
        (if (= current invalid-char)
          (get-valid-chars string rest)
          (cons current (get-valid-chars string rest)))))))

(defn append-string [string alphabet]
  (let [valid-chars (get-valid-chars string alphabet)]
    (if (empty? valid-chars)
      '()
      (let [current (first valid-chars)
            rest    (rest valid-chars)]
        (cons (str string current)
              (append-string string rest))))))

(defn expand-strings [strings alphabet]
  (if (empty? strings)
    '()
    (let [string      (first strings)
          rest        (rest strings)
          new-strings (append-string string alphabet)]
      (concat new-strings (expand-strings rest alphabet)))))

(defn gen-strings [alphabet n]
  (if (= 0 n)
    '("")
    (let [short (gen-strings alphabet (dec n))]
      (expand-strings short alphabet))))

(let [chars [\a \b \c]
      n 2]
  (println "1.1:" (gen-strings chars n)))