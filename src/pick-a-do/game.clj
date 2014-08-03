(ns pickado.game)

(defn- calculate-score [player field weight]
  (+ (or (field player) 0) weight -3))

(calculate-score {:12 1} :15 2)

(defn play- [player hit]
  (let [field (key hit)
        weight (val hit)]
    (assoc player field (+ (or (field player) 0) weight))))

(defn score [player hit]
  (let [field (key hit)
        weight (val hit)]
    (assoc player field (+ (or (field player) 0) weight))))


(defn throw-dart [player hit]
  (let [entry (first hit)
        field (key entry)
        value (val entry)]
    (assoc player field (+ value (if (contains? player field) (field player) 0)))))

;; Pickado
;; -------
;; Players play game. In one player round player throws three darts.
;; In one dart throw, dart hits table, adds score to player, and score other players.
