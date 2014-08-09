(ns pickado.game
  (:require [pickado.generator :as generator]))

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

(defn turn [player {:keys [field multiplayer]}]
  (assoc player field (+ (or (field player) 0) multiplayer)))

(defn new-game [players]
  {:field-area [0 0.80 0.90 1]
   :center-area [0 0.75 1]
   :center-to-field-ratio 1/61
   :fields 20
   :players (vec (repeat players {}))
   :round 0})

(defn split-players [players player]
  "Splits players on current and others."
  {:current player
   :others (filter #(not= % player) players)})

(defn player-round [game]
  (vec (map #(nth (iterate (fn [player] (turn player (one-throw game))) %) 3) (:players game))))

(defn round [game]
  (-> game
      (assoc-in [:players] (player-round game))
      (update-in [:round] inc)))

(defn match [game rounds]
  (nth (iterate round game) rounds))

;; loop round / map players / 3x / get {:score :player} / filter players without player / map score to players / assoc :player with map
