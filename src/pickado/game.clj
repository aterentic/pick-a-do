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
