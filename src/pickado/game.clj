(ns pickado.game
  (:use [pickado.generator :as generator]
        [pickado.score :as score]
        [pickado.rules :as rules]))

(defn one-throw [{:keys [fields field-area center-area center-to-field-ratio]}]
  (let [center? (< (rand) center-to-field-ratio)
        field (if center? :center (keyword (str (generator/field fields (rand)))))
        multiplayer (generator/multiplayer (if center? center-area field-area) (rand))]
    {:field field :multiplayer multiplayer}))

(defn player-turn [player hit]
  (let [{:keys [field multiplier]} hit]
    (assoc player field (score/player-score player hit))))

(defn score-value [score-amount]
  (let [{:keys [field multiplier]} score-amount]
    (if (= :center field)
      (* 25 multiplier)
      (* (read-string (name field)) multiplier))))

(defn score-player [player score-amount]
  (let [{:keys [field multiplier]} score-amount]
    (if (rules/closed? player field)
      player
      (assoc player :score (score-value score-amount)))))

  (defn turn [players hit]
    (cons
     (player-turn (first players))
     (rest players)))

  (defn new-game [players]
    {:field-area [0 0.80 0.90 1]
     :center-area [0 0.75 1]
     :center-to-field-ratio 1/61
     :fields 20
     :players (vec (repeat players {}))
     :round 0})

  (defn next-player [players]
    (conj (vec (rest players)) (first players)))

  (defn player-round [game]
    (vec (map #(nth (iterate (fn [player] (turn player (one-throw game))) %) 3) (:players game))))

  (defn round [game]
    (-> game
        (assoc-in [:players] (player-round game))
        (update-in [:round] inc)))

  (defn match [game rounds]
    (nth (iterate round game) rounds))
