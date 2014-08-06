(ns pickado.generator
  (:require [clojure.math.numeric-tower :as math]))

(defn field [fields random]
  "Returs field mapped to random number"
  (inc (math/round (math/floor (* fields random)))))

(defn multiplayer [ranges random]
  "Calculates multiplayer from given ranges [0...1] and value between 0 and 1."
  (count (filter #(<= % random) ranges)))
