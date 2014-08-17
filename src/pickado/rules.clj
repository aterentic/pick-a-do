(ns pickado.rules)

(defn closed? [player field]
  (>= (player field) 3))

(defn closed-all? [player]
  (every? (partial closed? player)
          [:15 :16 :17 :18 :19 :20 :center]))

(defn best? [players player]
  (<= (:score player) (apply min (map :score (filter closed-all? players)))))

(defn winner-exists? [players]
  (some closed-all? players))

(defn winner? [players player]
  (and
   (winner-exists? players)
   (closed-all? player)
   (best? players player)))
