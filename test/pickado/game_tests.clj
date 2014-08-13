(ns pickado.game-tests
  (:use midje.sweet pickado.game))

(fact "next-player cycles players so that next player is first."
      (next-player [{:12 2}]) => [{:12 2}]
      (next-player [{:16 1} {:17 2} {:11 3}]) => [{:17 2} {:11 3} {:16 1}]
      (next-player (next-player [{:16 2} {:17 2} {:11 3}])) => [{:11 3} {:16 2} {:17 2}])

(fact "new-player creates valid player."
      (new-player 1) => {:1 0 :2 0 :3 0 :4 0 :5 0 :6 0 :7 0 :8 0 :9 0 :10 0 :11 0 :12 0 :13 0 :14 0 :15 0 :16 0 :17 0 :18 0 :19 0 :20 0 :center 0 :score 0 :number 1}
      (new-player 2) => {:1 0 :2 0 :3 0 :4 0 :5 0 :6 0 :7 0 :8 0 :9 0 :10 0 :11 0 :12 0 :13 0 :14 0 :15 0 :16 0 :17 0 :18 0 :19 0 :20 0 :center 0 :score 0 :number 2})
