(ns pickado.game
  (:use [pickado.generator :as generator]))

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


(defn one-throw [{:keys [fields field-area center-area center-to-field-ratio]}]
  (let [center? (< (rand) center-to-field-ratio)
        field (if center? :center (keyword (str (generator/field fields (rand)))))
        multiplayer (generator/multiplayer (if center? center-area field-area) (rand))]
    {:field field :multiplayer multiplayer}))

(defn scored-so-far [player field]
  (or (field player) 0))

(defn- turn-amount [player field multiplier]
  (+ (scored-so-far player field) multiplier))

(defn turn [player {:keys [field multiplier]}]
  (assoc player field (turn-amount player field multiplier)))

(defn score-amount [player {:keys [field multiplier]}]
  {field (if (> (turn-amount player field multiplier) 3) (+ (- (field player) 3) multiplier) 0)})

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
