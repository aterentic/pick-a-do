(ns pickado.repl
  (:require [pickado.table :as table]
            [pickado.rules :as rules]
            [pickado.game :as game]))

(def pick-a-do {:field-area [0 0.80 0.90 1]
                :center-area [0 0.66 1]
                :center-to-field-ratio 1/61
                :round 0})

(def players [{:15 5 :16 3 :17 3 :18 3 :19 3 :20 3 :center 3 :score 205}
              {:15 5 :16 3 :17 3 :18 3 :19 3 :20 4 :center 3 :score 102}])


(game/throw-dart (first players) {:13 1})


(table/round pick-a-do)

(rules/winner? players (first players))
(rules/winner? players (second players))
