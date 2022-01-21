(ns clojure-challenges.scramble
  (:require [clojure.string :as str]))

(defn rearrangable?
  "returns true if a portion of big-str characters can be rearranged to match small-str, otherwise returns false.
  Worst possible case scenario comparison.
  Only considers lowercase a-z characters."
  [big-str small-str]
  (cond
    (nil? small-str) true
    (not (str/includes? big-str (str (first small-str)))) false
    :else (rearrangable? big-str (seq (rest small-str)))))

(comment
  (rearrangable? "ab" "b"))

(defn scramble?
  "returns true if a portion of big-str characters can be rearranged to match small-str, otherwise returns false.
  Efficient version.
  Only considers lowercase a-z characters."
  [big-str small-str]
  (cond
    (or (nil? big-str)
        (nil? small-str)
        (< (count big-str) (count small-str)))
    false

    (or (= big-str small-str)
        (str/includes? big-str small-str))
    true

    :else
    (rearrangable? big-str small-str)))
