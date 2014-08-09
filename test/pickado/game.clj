(ns pickado.test.game
  (:require midje.sweet pickado.game))

(fact "split-players splits players to current and other"
      (split-players
       [{:12 1} {:12 2 :14 3} {:15 2}] {:12 1}) => {:current {:12 1} :others [{:12 2 :14 3} {:15 2}]})
