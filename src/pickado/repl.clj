(ns pickado.repl
  (:require [pickado.generator :as generator]
            [pickado.rules :as rules]
            [pickado.game :as game]))

(def players [{:15 5 :16 3 :17 3 :18 3 :19 3 :20 3 :center 3 :score 205}
              {:15 5 :16 3 :17 3 :18 3 :19 3 :20 4 :center 3 :score 102}])

(def pick-a-do {:field-area [0 0.80 0.90 1]
                :center-area [0 0.66 1]
                :center-to-field-ratio 1/61
                :fields 20
                :players players
                :round 0})

(count (filter true? (repeatedly 100 (fn [] (let [game (game/match (game/new-game 4) 250)]
  (reduce #(or %1 %2) (map #(rules/winner? (:players game) %) (:players game))))))))
