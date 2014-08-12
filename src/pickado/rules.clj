(ns pickado.rules)

(defn closed? [player field]
  (>= (or (field player) 0) 3))

(defn closed-all? [player]
  (reduce
   (fn [result hits]
     (and result (>= hits 3)))
   (map #(if (nil? %) 0 %)
        (map player [:15 :16 :17 :18 :19 :20 :center]))))

(defn best? [players player]
  (<= (:score player) (reduce min (map :score players))))

(defn winner? [players player]
  (let [finished (filter closed-all? players)]
    (if (empty? finished)
      false
      (and (best? finished player) (closed-all? player)))))
