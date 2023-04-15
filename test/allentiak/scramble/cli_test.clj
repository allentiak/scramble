(ns allentiak.scramble.cli-test
  (:require
    [allentiak.scramble.backend :as backend]
    [allentiak.scramble.cli :as cli]
    [clojure.test :refer [deftest testing]]
    [expectations.clojure.test :refer [expect]])
  (:import
    (java.io
      StringWriter)))


(deftest main-test
  (testing "invoking -main"
    (let [letters1 "rekqodlw"
          word1 "world"
          letters2 "katas"
          word2 "steak"]
      ;; each test case should expect a different value (respectively, true and false).

      (testing "side-effect only"
        (expect (= (backend/scramble? letters1 word1)
                   (read-string
                     (with-out-str
                       (cli/-main "--letters" letters1 "--word" word1)))))
        (expect (= (backend/scramble? letters2 word2)
                   (read-string
                     (with-out-str
                       (cli/-main "--letters" letters2 "--word" word2))))))

      (testing "return-value only"
        (expect (= (backend/scramble? letters1 word1)
                   (binding
                     [*out* (StringWriter.)]
                     (cli/-main "--letters" letters1 "--word" word1))))
        (expect (= (backend/scramble? letters2 word2)
                   (binding
                     [*out* (StringWriter.)]
                     (cli/-main "--letters" letters2 "--word" word2))))))))
