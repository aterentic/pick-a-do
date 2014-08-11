(ns pickado.score)

(defn- scored-so-far [player {field :field}]
  (or (field player) 0))

(defn will-close? [player hit]
  "Checks if player will close field by scoring."
  (>= (player-score player hit) 3))

(defn player-score [player hit]
  "Calculates player score on hit."
  (+ (scored-so-far player hit) (:multiplier hit)))

(defn other-score [player hit]
  "Calculates amount of score to other players."
  (let [{:keys [field multiplier]} hit]
    {field (if (will-close? player hit) (+ multiplier (- (field player) 3)) 0)}))
