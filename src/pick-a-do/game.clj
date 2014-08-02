(ns pickado.game)

(defn throw-dart [player hit]
  (let [entry (first hit)
        field (key entry)
        value (val entry)]
    (assoc player field (+ value (if (contains? player field) (field player) 0)))))

