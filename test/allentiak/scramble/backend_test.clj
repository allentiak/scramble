(ns allentiak.scramble.backend-test
  (:require
    [allentiak.scramble.backend :refer [scramble?]]
    [clojure.test :refer [deftest testing]]
    [expectations.clojure.test :refer [expect]]))

(deftest scramble-test

  (testing "exceptional behaviors"
    ;; nil strings make the result false
    (expect (not (scramble? nil nil)))
    (expect (not (scramble? nil "")))
    (expect (not (scramble? "" nil))))

  (testing "degenerate case"
    ;; the first string should always be smaller or equal than the second one
    (expect (not (scramble? "ab" "abc")))
    (expect (not (scramble? "" "a"))))

  (testing "basic cases"
    ;; the same string should always match, even if it's blank
    (expect (scramble? "a" "a"))
    (expect (scramble? "abc" "abc"))
    (expect (scramble? "" "")))

  (testing "strict subset case"
    (expect (scramble? "abc" ""))
    (expect (scramble? "abc" "a"))
    (expect (scramble? "abc" "b"))
    (expect (scramble? "abc" "c"))
    (expect (scramble? "abc" "ab"))
    (expect (scramble? "abc" "bc")))

  (testing "second element is blank"
    (expect (scramble? "a" "")))

  (testing "non-strict subset case"
    (expect (scramble? "abc" "ac"))
    (expect (scramble? "abc" "ba"))
    (expect (scramble? "abc" "ca"))
    (expect (scramble? "abc" "cb")))

  (testing "repeated letters"
    (expect (scramble? "aaa" "a"))
    (expect (not (scramble? "a" "aaa"))))

  (testing "provided cases"
    (expect (scramble? "rekqodlw" "world"))
    (expect (scramble? "cedewaraaossoqqyt" "codewars"))
    (expect (not (scramble? "katas" "steak")))))
