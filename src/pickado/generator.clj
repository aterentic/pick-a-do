(ns pickado.generator
  (:require [clojure.math.numeric-tower :as math]))

(defn field [fields random]
  "Returs field mapped to random number [0,1)"
  (inc (math/round (math/floor (* fields random)))))

(defn multiplier [ranges random]
  "Calculates multiplier from given ranges [0...1] and random number [0,1)."
  (count (filter #(<= % random) ranges)))

(defn- center? [center-to-field-ratio randomizer]
  (< (randomizer) center-to-field-ratio))

(defn throw-dart [{:keys [fields field-area center-area center-to-field-ratio]} randomizer]
  (if (center? center-to-field-ratio randomizer)
    {:field :center
     :multiplier (multiplier center-area (randomizer))}
    {:field (keyword (str (field fields (randomizer))))
     :multiplier (multiplier field-area (randomizer))}))
