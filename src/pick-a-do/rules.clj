(ns pickado.rules)

(defn closed? [player field]
  (>= (field player) 3))

(defn closed-all? [player]
  (reduce
   (fn [result hits]
     (and result (>= hits 3)))
   (map player [:15 :16 :17 :18 :19 :20 :center])))

(defn best? [players player]
  (<= (:score player) (reduce min (map :score players))))

(defn winner? [players player]
  (let [finished (filter closed-all? players)]
    (if (empty? finished)
      false
      (and (best? finished player) (closed-all? player)))))
