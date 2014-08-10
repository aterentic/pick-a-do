(ns pickado.test.game
  (:use midje.sweet pickado.game))

(fact "split-players splits players to current and other"
      (split-players
       [{:12 1} {:12 2 :14 3} {:15 2}] {:12 1}) => {:current {:12 1} :others [{:12 2 :14 3} {:15 2}]})

(fact "has-current? is true if some player is current."
      (has-current? [{:current true} {} {}]) => true
      (has-current? [{} {:current true} {}]) => true)

(fact "has-current? is false if there are no current players."
      (has-current? [{:current false} {} {}]) => false
      (has-current? [{} {} {}]) => false)

(fact "next-player cycles players so that next player is first."
      (next-player [{:12 2}]) => [{:12 2}]
      (next-player [{:16 1} {:17 2} {:11 3}]) => [{:17 2} {:11 3} {:16 1}]
      (next-player (next-player [{:16 2} {:17 2} {:11 3}])) => [{:11 3} {:16 2} {:17 2}])
