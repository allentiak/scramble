(ns clojure-challenges.scramble-test
  (:require [clojure-challenges.scramble :refer :all]
            [clojure.test :refer [deftest is testing]]))

(deftest scramble-test
  (testing "degenerate case"
    ;; the second string should always be smaller or equal than the second one
    (is (= (scramble? "ab" "abc") false))
    (is (= (scramble? "" "a") false)))

  (testing "basic cases"
    ;; the same string should always match, even if it's blank
    (is (= (scramble? "a" "a") true))
    (is (= (scramble? "abc" "abc") true))
    (is (= (scramble? "" "") true))))
