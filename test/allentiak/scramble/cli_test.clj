(ns allentiak.scramble.cli-test
  (:require [allentiak.scramble.cli :as cli]
            [clojure.test :refer [deftest is testing]]
            [clojure.string :as str]))

(deftest main-test
  (testing "invoking -main"
    (is (= true (cli/-main "--letters" "rekqodlw" "--word" "world")))
    (is (= false (cli/-main "--letters" "katas" "--word" "steak")))))
