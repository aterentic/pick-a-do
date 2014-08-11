(ns pickado.game-tests
  (:use midje.sweet pickado.game))

(fact "next-player cycles players so that next player is first."
      (next-player [{:12 2}]) => [{:12 2}]
      (next-player [{:16 1} {:17 2} {:11 3}]) => [{:17 2} {:11 3} {:16 1}]
      (next-player (next-player [{:16 2} {:17 2} {:11 3}])) => [{:11 3} {:16 2} {:17 2}])
