(ns allentiak.scramble.cli-test
  (:require
    [allentiak.scramble.backend :as backend]
    [allentiak.scramble.cli :as cli]
    [clojure.test :refer [deftest is testing]]
    [clojure.string :as str]))

(deftest main-test
  (testing "invoking -main"
    (let [letters1 "rekqodlw"
          word1 "world"
          letters2 "katas"
          word2 "steak"]
      ;; each test case should expect a different value (respectively, true and false).
      (is (= (backend/scramble? letters1 word1) (cli/-main "--letters" letters1 "--word" word1)))
      (is (= (backend/scramble? letters2 word2) (cli/-main "--letters" letters2 "--word" word2))))))
