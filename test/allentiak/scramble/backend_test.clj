(ns allentiak.scramble.backend-test
  (:require
   [allentiak.scramble.backend :refer [scramble?]]
   [clojure.test :refer [deftest is testing]]))

(deftest scramble-test

  (testing "exceptional behaviors"
    ;; nil strings make the result false
    (is (= false (scramble? nil nil)))
    (is (= false (scramble? nil "")))
    (is (= false (scramble? "" nil))))

  (testing "degenerate case"
    ;; the first string should always be smaller or equal than the second one
    (is (= false (scramble? "ab" "abc")))
    (is (= false (scramble? "" "a"))))

  (testing "basic cases"
    ;; the same string should always match, even if it's blank
    (is (= true (scramble? "a" "a")))
    (is (= true (scramble? "abc" "abc")))
    (is (= true (scramble? "" ""))))

  (testing "strict subset case"
    (is (= true (scramble? "abc" "")))
    (is (= true (scramble? "abc" "a")))
    (is (= true (scramble? "abc" "b")))
    (is (= true (scramble? "abc" "c")))
    (is (= true (scramble? "abc" "ab")))
    (is (= true (scramble? "abc" "bc"))))

  (testing "second element is blank"
    (is (= true (scramble? "a" ""))))

  (testing "non-strict subset case"
    (is (= true (scramble? "abc" "ac")))
    (is (= true (scramble? "abc" "ba")))
    (is (= true (scramble? "abc" "ca")))
    (is (= true (scramble? "abc" "cb"))))

  (testing "repeated letters"
    (is (= true (scramble? "aaa" "a")))
    (is (= false (scramble? "a" "aaa"))))

  (testing "provided cases"
    (is (= true (scramble? "rekqodlw" "world")))
    (is (= true (scramble? "cedewaraaossoqqyt" "codewars")))
    (is (= false (scramble? "katas" "steak")))))
