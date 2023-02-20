(ns allentiak.scramble-test
  (:require
    [allentiak.scramble :refer [scramble?]]
    [clojure.test :refer [deftest is testing]]))


(deftest scramble-test

  (testing "exceptional behaviors"
    ;; nil strings make the result false
    (is (= (scramble? nil nil) false))
    (is (= (scramble? nil "") false))
    (is (= (scramble? "" nil) false)))

  (testing "degenerate case"
    ;; the second string should always be smaller or equal than the second one
    (is (= (scramble? "ab" "abc") false))
    (is (= (scramble? "" "a") false)))

  (testing "basic cases"
    ;; the same string should always match, even if it's blank
    (is (= (scramble? "a" "a") true))
    (is (= (scramble? "abc" "abc") true))
    (is (= (scramble? "" "") true)))

  (testing "strict subset case"
    (is (= (scramble? "abc" "") true))
    (is (= (scramble? "abc" "a") true))
    (is (= (scramble? "abc" "b") true))
    (is (= (scramble? "abc" "c") true))
    (is (= (scramble? "abc" "ab") true))
    (is (= (scramble? "abc" "bc") true)))

  (testing "second element is blank"
    (is (= (scramble? "a" "") true)))

  (testing "non-strict subset case"
    (is (= (scramble? "abc" "ac") true))
    (is (= (scramble? "abc" "ba") true))
    (is (= (scramble? "abc" "ca") true))
    (is (= (scramble? "abc" "cb") true)))

  (testing "provided cases"
    (is (= (scramble? "rekqodlw" "world") true))
    (is (= (scramble? "cedewaraaossoqqyt" "codewars") true))
    (is (= (scramble? "katas" "steak") false))))
