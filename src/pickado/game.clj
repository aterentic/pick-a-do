(ns pickado.game
  (:use [pickado.generator :as generator]
        [pickado.score :as score]
        [pickado.rules :as rules]))

(defn player-turn [player hit]
    (assoc player
      (:field hit)
      (score/player-score player hit)))

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
  (vec (cons
        (player-turn (first players) hit)
        (map #(score-player (score/other-score (first players) hit) %) (rest players)))))

(defn next-player [players]
  (conj (vec (rest players)) (first players)))

(defn move [game]
  (let [hit (generator/throw-dart game rand)]
    (update-in game [:players] #(turn % hit))))

(defn three-moves [game]
  (nth (iterate move game) 3))

(defn round [game]
  (update-in (three-moves game) [:players] next-player))

(defn match [game rounds]
  (nth (iterate round game) rounds))

(defn new-player [number]
  (reduce #(assoc %1 %2 0)
          {:center 0 :score 0 :number number}
          (map (comp keyword str) (range 1 21))))

(defn new-game [players]
  {:field-area [0 0.80 0.90 1]
   :center-area [0 0.75 1]
   :center-to-field-ratio 1/61
   :fields 20
   :players (vec (map new-player (range 1 (inc players))))
   :round 0})
