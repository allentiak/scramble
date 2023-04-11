(ns allentiak.scramble.backend-test
  (:require
   [allentiak.scramble.backend :refer [scramble?]]
   [clojure.test :refer [deftest is testing]]))

(deftest scramble-test

  (testing "exceptional behaviors"
    ;; nil strings make the result false
    (is (not (scramble? nil nil)))
    (is (not (scramble? nil "")))
    (is (not (scramble? "" nil))))

  (testing "degenerate case"
    ;; the first string should always be smaller or equal than the second one
    (is (not (scramble? "ab" "abc")))
    (is (not (scramble? "" "a"))))

  (testing "basic cases"
    ;; the same string should always match, even if it's blank
    (is (scramble? "a" "a"))
    (is (scramble? "abc" "abc"))
    (is (scramble? "" "")))

  (testing "strict subset case"
    (is (scramble? "abc" ""))
    (is (scramble? "abc" "a"))
    (is (scramble? "abc" "b"))
    (is (scramble? "abc" "c"))
    (is (scramble? "abc" "ab"))
    (is (scramble? "abc" "bc")))

  (testing "second element is blank"
    (is (scramble? "a" "")))

  (testing "non-strict subset case"
    (is (scramble? "abc" "ac"))
    (is (scramble? "abc" "ba"))
    (is (scramble? "abc" "ca"))
    (is (scramble? "abc" "cb")))

  (testing "repeated letters"
    (is (scramble? "aaa" "a"))
    (is (not (scramble? "a" "aaa"))))

  (testing "provided cases"
    (is (scramble? "rekqodlw" "world"))
    (is (scramble? "cedewaraaossoqqyt" "codewars"))
    (is (not (scramble? "katas" "steak")))))
