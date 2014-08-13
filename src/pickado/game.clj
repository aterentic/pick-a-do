(ns pickado.game
  (:use [pickado.generator :as generator]
        [pickado.score :as score]
        [pickado.rules :as rules]))

(defn one-throw [{:keys [fields field-area center-area center-to-field-ratio]}]
  (let [center? (< (rand) center-to-field-ratio)
        field (if center? :center (keyword (str (generator/field fields (rand)))))
        multiplier (generator/multiplier (if center? center-area field-area) (rand))]
    {:field field :multiplier multiplier}))

(defn player-turn [player hit]
  (let [{:keys [field multiplier]} hit]
    (assoc player field (score/player-score player hit))))

(defn score-value [score-amount]
  (let [{:keys [field multiplier]} score-amount]
    (if (= :center field)
      (* 25 multiplier)
      (* (read-string (name field)) multiplier))))

(defn score-player [score-amount player]
  (let [{:keys [field multiplier]} score-amount]
    (if (rules/closed? player field)
      player
      (assoc player :score (score-value score-amount)))))

(defn turn [players hit]
  (cons
   (player-turn (first players) hit)
   (map #(score-player (score/other-score (first players) hit) %) (rest players))))

(defn new-player [number]
  (reduce #(assoc %1 %2 0)
          {:center 0 :score 0 :number number}
          (map (comp keyword str) (range 1 21))))

(defn new-game [players]
  {:field-area [0 0.80 0.90 1]
   :center-area [0 0.75 1]
   :center-to-field-ratio 1/61
   :fields 20
   :players (vec (map new-player (range 1 players)))
   :round 0})

(defn next-player [players]
  (conj (vec (rest players)) (first players)))

(defn game-turn [game]
  (assoc game :players (vec (turn (:players game) (one-throw game)))))

(defn round [game]
  (assoc game :players (next-player (:players (nth (iterate game-turn game) 3)))))

(defn match [game rounds]
  (nth (iterate round game) rounds))
